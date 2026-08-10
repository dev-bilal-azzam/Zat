package com.devbilal.data.util

import com.devbilal.data.datasource.local.database.attachment.AttachmentDao
import com.devbilal.data.datasource.local.database.attachment.AttachmentDto
import com.devbilal.data.datasource.local.database.attachment.toEntity
import com.devbilal.data.datasource.local.database.attachment.toDto
import com.devbilal.domain.entity.Attachment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Manages the storage and deduplication logic for media attachments.
 */
class AttachmentManager(
    private val attachmentDao: AttachmentDao,
    private val fileManager: FileManager,
) {
    /**
     * Saves a list of attachments to disk using content-based hashing to avoid duplicates.
     * Returns a list of [AttachmentDto] that can be linked in the database.
     */
    suspend fun saveAttachments(attachments: List<Attachment>): List<AttachmentDto> = withContext(Dispatchers.IO) {
        attachments.map { attachment ->
            val hash = fileManager.calculateHash(attachment.filePath)
            val extension = attachment.filePath.substringAfterLast('.', "")
            val fileName = "$hash.$extension"
            
            // Check if attachment with this hash already exists in metadata
            val existing = attachmentDao.getAttachmentByHash(hash)
            
            val thumbnailHash = if (attachment is Attachment.Video) {
                // Deduplicate thumbnails by appending a suffix to the video hash
                val thumbHash = hash + "_thumb"
                val thumbName = "$thumbHash.jpg"
                if (attachmentDao.getAttachmentByHash(thumbHash) == null) {
                    fileManager.saveFile(thumbName, attachment.thumbnail)
                    attachmentDao.insertAttachment(
                         AttachmentDto(
                            hash = thumbHash,
                            extension = "jpg",
                            type = "IMAGE",
                            size = attachment.thumbnail.size.toLong(),
                            filePath = fileManager.getAttachmentPath(thumbName),
                            createdAt = attachment.createdAt.toString()
                        )
                    )
                }
                thumbHash
            } else null

            if (existing != null) {
                // If content already exists, just return the existing entity to be linked
                existing
            } else {
                // Save new physical file and create metadata
                val path = fileManager.copyFile(attachment.filePath, fileName)
                val size = fileManager.getFileSize(path)
                val entity = attachment.toDto(hash, path, size, thumbnailHash)
                attachmentDao.insertAttachment(entity)
                entity
            }
        }
    }

    /**
     * Converts a list of database entities back into domain-layer [Attachment] objects.
     */
    suspend fun getAttachments(entities: List<AttachmentDto>): List<Attachment> = withContext(Dispatchers.IO) {
        entities.map { entity ->
            val thumbnail = entity.thumbnailHash?.let { thumbHash ->
                attachmentDao.getAttachmentByHash(thumbHash)?.let { thumbEntity ->
                    fileManager.readFile(thumbEntity.filePath)
                }
            }
            entity.toEntity(thumbnail)
        }
    }

    /**
     * Purges physical files and database metadata for attachments that are no longer
     * referenced by any active Diary Entry or History Version.
     */
    suspend fun cleanupOrphanedAttachments() = withContext(Dispatchers.IO) {
        val orphans = attachmentDao.getOrphanedAttachments()
        orphans.forEach { orphan ->
            fileManager.deleteFile(orphan.filePath)
            attachmentDao.deleteAttachment(orphan.hash)
        }
    }
}

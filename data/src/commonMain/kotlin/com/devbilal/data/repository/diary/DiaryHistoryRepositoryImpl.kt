@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.diaryentry.AttachmentDto
import com.devbilal.data.datasource.local.database.diaryentry.toDto
import com.devbilal.data.datasource.local.database.diaryentry.toEntity
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryHistoryDao
import com.devbilal.data.datasource.local.database.diaryhistory.toDto
import com.devbilal.data.datasource.local.database.diaryhistory.toEntity
import com.devbilal.data.util.FileManager
import com.devbilal.domain.entity.Attachment
import com.devbilal.domain.entity.DiaryVersion
import com.devbilal.domain.repository.DiaryHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DiaryHistoryRepositoryImpl(
    private val diaryHistoryDao: DiaryHistoryDao,
    private val fileManager: FileManager
) : DiaryHistoryRepository {

    override suspend fun saveVersion(version: DiaryVersion) {
        val attachmentDtos = processAttachments(version.entry.attachments)
        diaryHistoryDao.insertVersion(version.toDto(attachmentDtos))
    }

    override suspend fun deleteVersion(versionId: Uuid) {
        diaryHistoryDao.softDeleteVersion(versionId.toString())
    }

    override suspend fun deleteHistoryByEntryId(entryId: Uuid) {
        diaryHistoryDao.softDeleteHistoryByEntryId(entryId.toString())
    }

    override fun getHistoryByEntryId(entryId: Uuid): Flow<List<DiaryVersion>> {
        return diaryHistoryDao.getHistoryByEntryId(entryId.toString()).map { list ->
            list.map { dto ->
                val attachments = dto.attachments.map { attachmentDto ->
                    val thumbnail = if (attachmentDto is AttachmentDto.Video) {
                        fileManager.readFile(attachmentDto.thumbnailFilePath)
                    } else null
                    attachmentDto.toEntity(attachmentDto.filePath, thumbnail)
                }
                dto.toEntity(attachments)
            }
        }
    }

    private suspend fun processAttachments(attachments: List<Attachment>): List<AttachmentDto> {
        return attachments.map { attachment ->
            val fileExtension = attachment.filePath.substringAfterLast('.', "bin")
            val fileName = "history_${attachment.id}_${attachment.type}.$fileExtension"

            val path = fileManager.copyFile(attachment.filePath, fileName)
            val thumbnailPath = if (attachment is Attachment.Video) {
                val thumbName = "history_${attachment.id}_${attachment.type}_thumb.jpg"
                fileManager.saveFile(thumbName, attachment.thumbnail)
            } else null

            attachment.toDto(path, thumbnailPath)
        }
    }
}
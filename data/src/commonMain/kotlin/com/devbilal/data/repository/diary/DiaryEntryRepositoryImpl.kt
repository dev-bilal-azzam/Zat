@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.diaryentry.AttachmentDto
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryentry.toDto
import com.devbilal.data.datasource.local.database.diaryentry.toEntity
import com.devbilal.data.datasource.local.database.diaryentry.toSummary
import com.devbilal.data.util.FileManager
import com.devbilal.domain.entity.Attachment
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DiaryEntryRepositoryImpl(
    private val diaryEntryDao: DiaryEntryDao,
    private val fileManager: FileManager
): DiaryEntryRepository {
    override suspend fun saveEntry(entry: DiaryEntry) {
        val attachmentsWithPaths = entry.attachments.map { attachment ->
            val fileName = "${attachment.id}_${attachment.type}.bin"
            val path = fileManager.saveFile(fileName, attachment.bytes)
            val thumbnailPath = if (attachment is Attachment.Video) {
                val thumbName = "${attachment.id}_${attachment.type}_thumb.jpg"
                fileManager.saveFile(thumbName, attachment.thumbnail)
            } else null
            attachment.toDto(path, thumbnailPath)
        }
        diaryEntryDao.insertEntry(entry.toDto(attachmentsWithPaths))
    }

    override suspend fun updateEntry(entry: DiaryEntry) {
        val attachmentsWithPaths = entry.attachments.map { attachment ->
            val fileName = "${attachment.id}_${attachment.type}.bin"
            val path = fileManager.saveFile(fileName, attachment.bytes)
            val thumbnailPath = if (attachment is Attachment.Video) {
                val thumbName = "${attachment.id}_${attachment.type}_thumb.jpg"
                fileManager.saveFile(thumbName, attachment.thumbnail)
            } else null
            attachment.toDto(path, thumbnailPath)
        }
        diaryEntryDao.updateEntry(entry.toDto(attachmentsWithPaths))
    }

    override suspend fun deleteEntry(id: Uuid) {
        val entry = diaryEntryDao.getEntryById(id.toString())
        entry?.attachments?.forEach { attachment ->
            fileManager.deleteFile(attachment.filePath)
            if (attachment is AttachmentDto.Video) {
                fileManager.deleteFile(attachment.thumbnailFilePath)
            }
        }
        diaryEntryDao.softDeleteEntry(id.toString())
    }

    override suspend fun getEntryById(id: Uuid): DiaryEntry? {
        val dto = diaryEntryDao.getEntryById(id.toString()) ?: return null
        val historyCount = diaryEntryDao.getHistoryCount(id.toString())

        val attachmentsWithBytes = dto.attachments.map { attachmentDto ->
            val bytes = fileManager.readFile(attachmentDto.filePath) ?: byteArrayOf()
            val thumbnail = if (attachmentDto is AttachmentDto.Video) {
                fileManager.readFile(attachmentDto.thumbnailFilePath)
            } else null
            attachmentDto.toEntity(bytes, thumbnail)
        }

        return dto.toEntity(historyCount, attachmentsWithBytes)
    }

    override fun getAllEntries(): Flow<List<DiaryEntrySummary>> {
        return diaryEntryDao.getAllEntriesWithHistoryCount().map { list ->
            list.map { it.toSummary() }
        }
    }
}
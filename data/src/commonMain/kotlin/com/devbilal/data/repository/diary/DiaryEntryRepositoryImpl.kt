@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryentry.toDto
import com.devbilal.data.datasource.local.database.diaryentry.toEntity
import com.devbilal.data.datasource.local.database.diaryentry.toSummary
import com.devbilal.data.util.FileManager
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
            attachment.toDto(path)
        }
        diaryEntryDao.insertEntry(entry.toDto(attachmentsWithPaths))
    }

    override suspend fun updateEntry(entry: DiaryEntry) {
        // Simple update: save new files, update paths
        // Ideal: delete old files that are no longer in attachments
        val attachmentsWithPaths = entry.attachments.map { attachment ->
            val fileName = "${attachment.id}_${attachment.type}.bin"
            val path = fileManager.saveFile(fileName, attachment.bytes)
            attachment.toDto(path)
        }
        diaryEntryDao.updateEntry(entry.toDto(attachmentsWithPaths))
    }

    override suspend fun deleteEntry(id: Uuid) {
        val entry = diaryEntryDao.getEntryById(id.toString())
        entry?.attachments?.forEach { attachment ->
            fileManager.deleteFile(attachment.filePath)
        }
        diaryEntryDao.softDeleteEntry(id.toString())
    }

    override suspend fun getEntryById(id: Uuid): DiaryEntry? {
        val dto = diaryEntryDao.getEntryById(id.toString()) ?: return null
        val historyCount = diaryEntryDao.getHistoryCount(id.toString())

        val attachmentsWithBytes = dto.attachments.map { attachmentDto ->
            val bytes = fileManager.readFile(attachmentDto.filePath) ?: byteArrayOf()
            attachmentDto.toEntity(bytes)
        }

        return dto.toEntity(historyCount, attachmentsWithBytes)
    }

    override fun getAllEntries(): Flow<List<DiaryEntrySummary>> {
        return diaryEntryDao.getAllEntriesWithHistoryCount().map { list ->
            list.map { it.toSummary() }
        }
    }
}
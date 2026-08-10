@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.attachment.AttachmentDao
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryentry.toDto
import com.devbilal.data.datasource.local.database.diaryentry.toEntity
import com.devbilal.data.datasource.local.database.diaryentry.toSummary
import com.devbilal.data.util.AttachmentManager
import com.devbilal.data.util.FileManager
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Implementation of the [DiaryEntryRepository] handling optimized attachment storage.
 */
class DiaryEntryRepositoryImpl(
    private val diaryEntryDao: DiaryEntryDao,
    private val attachmentDao: AttachmentDao,
    private val attachmentManager: AttachmentManager,
    private val fileManager: FileManager
): DiaryEntryRepository {

    override suspend fun saveEntry(entry: DiaryEntry) {
        val attachmentEntities = attachmentManager.saveAttachments(entry.attachments)
        diaryEntryDao.insertEntry(entry.toDto())
        attachmentDao.updateEntryAttachments(entry.id.toString(), attachmentEntities)
    }

    override suspend fun updateEntry(entry: DiaryEntry) {
        val attachmentEntities = attachmentManager.saveAttachments(entry.attachments)
        diaryEntryDao.updateEntry(entry.toDto())
        attachmentDao.updateEntryAttachments(entry.id.toString(), attachmentEntities)
    }

    override suspend fun deleteEntry(id: Uuid) {
        diaryEntryDao.softDeleteEntry(id.toString())
        attachmentManager.cleanupOrphanedAttachments()
    }

    override suspend fun getEntryById(id: Uuid): DiaryEntry? {
        val dtoWithAttachments = diaryEntryDao.getEntryWithAttachmentsById(id.toString()) ?: return null
        val historyCount = diaryEntryDao.getHistoryCount(id.toString())

        val attachments = attachmentManager.getAttachments(dtoWithAttachments.attachments)

        return dtoWithAttachments.entry.toEntity(historyCount, attachments)
    }

    override fun getAllEntries(): Flow<List<DiaryEntrySummary>> {
        return diaryEntryDao.getAllEntriesWithAttachmentsAndHistoryCount().map { list ->
            list.map { item ->
                item.entry.toSummary(
                    historyCount = item.historyCount,
                    attachmentTypes = item.attachments.map { 
                        com.devbilal.domain.entity.AttachmentType.valueOf(it.type)
                    }.distinct()
                )
            }
        }
    }

    override suspend fun clearTempCache() {
        fileManager.clearTempCache()
    }

    override suspend fun cleanupOrphanedAttachments() {
        attachmentManager.cleanupOrphanedAttachments()
    }
}

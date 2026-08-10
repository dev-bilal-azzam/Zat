@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.attachment.AttachmentDao
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryHistoryDao
import com.devbilal.data.datasource.local.database.diaryhistory.toDto
import com.devbilal.data.datasource.local.database.diaryhistory.toEntity
import com.devbilal.data.util.AttachmentManager
import com.devbilal.domain.entity.DiaryVersion
import com.devbilal.domain.repository.DiaryHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Implementation of the [DiaryHistoryRepository] handling historical diary versions and their attachments.
 */
class DiaryHistoryRepositoryImpl(
    private val diaryHistoryDao: DiaryHistoryDao,
    private val attachmentDao: AttachmentDao,
    private val attachmentManager: AttachmentManager
) : DiaryHistoryRepository {

    override suspend fun saveVersion(version: DiaryVersion) {
        val attachmentEntities = attachmentManager.saveAttachments(version.entry.attachments)
        diaryHistoryDao.insertVersion(version.toDto())
        attachmentDao.updateVersionAttachments(version.id.toString(), attachmentEntities)
    }

    override suspend fun deleteVersion(versionId: Uuid) {
        diaryHistoryDao.softDeleteVersion(versionId.toString())
        attachmentManager.cleanupOrphanedAttachments()
    }

    override suspend fun deleteHistoryByEntryId(entryId: Uuid) {
        diaryHistoryDao.softDeleteHistoryByEntryId(entryId.toString())
        attachmentManager.cleanupOrphanedAttachments()
    }

    override fun getHistoryByEntryId(entryId: Uuid): Flow<List<DiaryVersion>> {
        return diaryHistoryDao.getHistoryWithAttachmentsByEntryId(entryId.toString()).map { list ->
            list.map { item ->
                val attachments = attachmentManager.getAttachments(item.attachments)
                item.version.toEntity(attachments)
            }
        }
    }
}

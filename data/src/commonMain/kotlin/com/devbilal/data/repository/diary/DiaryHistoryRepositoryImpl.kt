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
): DiaryHistoryRepository {
    override suspend fun saveVersion(version: DiaryVersion) {
        val attachmentDtos = version.entry.attachments.map { attachment ->
            val fileName = "${attachment.id}_${attachment.type}.bin"
            val path = fileManager.saveFile(fileName, attachment.bytes)
            val thumbnailPath = if (attachment is Attachment.Video) {
                val thumbName = "${attachment.id}_${attachment.type}_thumb.jpg"
                fileManager.saveFile(thumbName, attachment.thumbnail)
            } else null
            attachment.toDto(path, thumbnailPath)
        }
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
                val attachmentsWithBytes = dto.attachments.map { attachmentDto ->
                    val bytes = fileManager.readFile(attachmentDto.filePath) ?: byteArrayOf()
                    val thumbnail = if (attachmentDto is AttachmentDto.Video) {
                        fileManager.readFile(attachmentDto.thumbnailFilePath)
                    } else null
                    attachmentDto.toEntity(bytes, thumbnail)
                }
                dto.toEntity(attachmentsWithBytes)
            }
        }
    }
}

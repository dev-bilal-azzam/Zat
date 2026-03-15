@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.diaryhistory.DiaryHistoryDao
import com.devbilal.domain.entity.DiaryVersion
import com.devbilal.domain.repository.DiaryHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DiaryHistoryRepositoryImpl(
    private val diaryHistoryDao: DiaryHistoryDao
): DiaryHistoryRepository {
    override suspend fun saveVersion(version: DiaryVersion) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVersion(versionId: Uuid) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHistoryByEntryId(entryId: Uuid) {
        TODO("Not yet implemented")
    }

    override fun getHistoryByEntryId(entryId: Uuid): Flow<List<DiaryVersion>> {
        TODO("Not yet implemented")
    }
}
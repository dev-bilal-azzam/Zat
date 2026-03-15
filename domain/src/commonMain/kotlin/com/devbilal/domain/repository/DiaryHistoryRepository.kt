@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.repository

import com.devbilal.domain.entity.DiaryVersion
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface DiaryHistoryRepository {
    suspend fun saveVersion(version: DiaryVersion)
    suspend fun deleteVersion(versionId: Uuid)
    suspend fun deleteHistoryByEntryId(entryId: Uuid)
    fun getHistoryByEntryId(entryId: Uuid): Flow<List<DiaryVersion>>
}

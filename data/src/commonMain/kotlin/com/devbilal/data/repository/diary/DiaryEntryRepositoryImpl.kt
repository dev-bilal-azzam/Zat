@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DiaryEntryRepositoryImpl(
    private val diaryEntryDao: DiaryEntryDao
): DiaryEntryRepository {
    override suspend fun saveEntry(entry: DiaryEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun updateEntry(entry: DiaryEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntry(id: Uuid) {
        TODO("Not yet implemented")
    }

    override suspend fun getEntryById(id: Uuid): DiaryEntry? {
        TODO("Not yet implemented")
    }

    override fun getAllEntries(): Flow<List<DiaryEntrySummary>> {
        TODO("Not yet implemented")
    }
}
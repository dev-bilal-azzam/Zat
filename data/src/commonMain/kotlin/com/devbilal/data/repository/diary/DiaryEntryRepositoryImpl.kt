@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.repository.diary

import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryentry.toDto
import com.devbilal.data.datasource.local.database.diaryentry.toEntity
import com.devbilal.data.datasource.local.database.diaryentry.toSummary
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DiaryEntryRepositoryImpl(
    private val diaryEntryDao: DiaryEntryDao
): DiaryEntryRepository {
    override suspend fun saveEntry(entry: DiaryEntry) {
        diaryEntryDao.insertEntry(entry.toDto())
    }

    override suspend fun updateEntry(entry: DiaryEntry) {
        diaryEntryDao.updateEntry(entry.toDto())
    }

    override suspend fun deleteEntry(id: Uuid) {
        diaryEntryDao.softDeleteEntry(id.toString())
    }

    override suspend fun getEntryById(id: Uuid): DiaryEntry? {
        val dto = diaryEntryDao.getEntryById(id.toString()) ?: return null
        val historyCount = diaryEntryDao.getHistoryCount(id.toString())
        return dto.toEntity(historyCount)
    }

    override fun getAllEntries(): Flow<List<DiaryEntrySummary>> {
        return diaryEntryDao.getAllEntriesWithHistoryCount().map { list ->
            list.map { it.toSummary() }
        }
    }
}
@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.repository

import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryEntrySummary
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface DiaryEntryRepository {
    suspend fun saveEntry(entry: DiaryEntry)
    suspend fun updateEntry(entry: DiaryEntry)
    suspend fun deleteEntry(id: Uuid)
    suspend fun getEntryById(id: Uuid): DiaryEntry?
    fun getAllEntries(): Flow<List<DiaryEntrySummary>>
}

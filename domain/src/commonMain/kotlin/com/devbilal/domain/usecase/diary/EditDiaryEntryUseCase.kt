@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryVersion
import com.devbilal.domain.repository.DiaryHistoryRepository
import com.devbilal.domain.repository.DiaryEntryRepository
import com.devbilal.domain.util.now
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi

class EditDiaryEntryUseCase(
    private val diaryEntryRepository: DiaryEntryRepository,
    private val historyRepository: DiaryHistoryRepository
) {
    suspend operator fun invoke(updatedEntry: DiaryEntry) {
        val oldEntry = diaryEntryRepository.getEntryById(updatedEntry.id)
            ?: throw IllegalArgumentException("Entry not found")

        // Create a version from the old entry before updating
        val version = DiaryVersion(
            primaryEntryId = oldEntry.id,
            entry = oldEntry,
            versionCreatedAt = LocalDateTime.now()
        )

        historyRepository.saveVersion(version)
        
        diaryEntryRepository.updateEntry(updatedEntry.copy(
            historyCount = oldEntry.historyCount + 1
        ))
    }
}

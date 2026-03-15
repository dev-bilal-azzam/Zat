@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.repository.DiaryHistoryRepository
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteDiaryEntryUseCase(
    private val diaryEntryRepository: DiaryEntryRepository,
    private val historyRepository: DiaryHistoryRepository
) {
    suspend operator fun invoke(id: Uuid) {
        historyRepository.deleteHistoryByEntryId(id)
        diaryEntryRepository.deleteEntry(id)
    }
}

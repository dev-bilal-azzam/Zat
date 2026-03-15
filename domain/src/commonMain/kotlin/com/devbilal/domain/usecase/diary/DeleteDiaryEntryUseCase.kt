@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.repository.DiaryHistoryRepository
import com.devbilal.domain.repository.DiaryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val historyRepository: DiaryHistoryRepository
) {
    suspend operator fun invoke(id: Uuid) {
        historyRepository.deleteHistoryByEntryId(id)
        diaryRepository.deleteEntry(id)
    }
}

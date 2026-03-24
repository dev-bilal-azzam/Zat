@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.repository.DiaryHistoryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DeleteDiaryVersionUseCase(
    private val historyRepository: DiaryHistoryRepository
) {
    suspend operator fun invoke(entryId: Uuid, versionId: Uuid) {
        historyRepository.deleteVersion(versionId)
    }
}

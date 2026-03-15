@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.entity.DiaryVersion
import com.devbilal.domain.repository.DiaryHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetDiaryHistoryUseCase(
    private val repository: DiaryHistoryRepository
) {
    operator fun invoke(entryId: Uuid): Flow<List<DiaryVersion>> {
        return repository.getHistoryByEntryId(entryId)
    }
}

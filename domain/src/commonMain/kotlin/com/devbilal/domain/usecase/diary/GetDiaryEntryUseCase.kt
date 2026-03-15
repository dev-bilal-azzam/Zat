@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetDiaryEntryUseCase(
    private val repository: DiaryEntryRepository
) {
    suspend operator fun invoke(id: Uuid): DiaryEntry? {
        return repository.getEntryById(id)
    }
}

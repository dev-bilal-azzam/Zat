package com.devbilal.domain.usecase.diary

import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.domain.repository.DiaryEntryRepository
import kotlinx.coroutines.flow.Flow

class GetAllDiaryEntriesUseCase(
    private val repository: DiaryEntryRepository
) {
    operator fun invoke(): Flow<List<DiaryEntrySummary>> {
        return repository.getAllEntries()
    }
}

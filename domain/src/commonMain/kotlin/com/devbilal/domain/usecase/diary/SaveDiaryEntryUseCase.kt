package com.devbilal.domain.usecase.diary

import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.repository.DiaryEntryRepository

class SaveDiaryEntryUseCase(
    private val repository: DiaryEntryRepository
) {
    suspend operator fun invoke(entry: DiaryEntry) {
        repository.saveEntry(entry)
    }
}

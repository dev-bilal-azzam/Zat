package com.devbilal.domain.usecase.diary

import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.repository.DiaryRepository

class SaveDiaryEntryUseCase(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(entry: DiaryEntry) {
        repository.saveEntry(entry)
    }
}

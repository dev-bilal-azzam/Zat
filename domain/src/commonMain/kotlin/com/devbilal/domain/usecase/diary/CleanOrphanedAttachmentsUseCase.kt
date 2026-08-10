package com.devbilal.domain.usecase.diary

import com.devbilal.domain.repository.DiaryEntryRepository

class CleanOrphanedAttachmentsUseCase(
    private val repository: DiaryEntryRepository
) {
    suspend operator fun invoke() {
        repository.cleanupOrphanedAttachments()
    }
}

package com.devbilal.domain.usecase.diary

import com.devbilal.domain.repository.DiaryEntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ClearTempCacheUseCase(
    private val repository: DiaryEntryRepository,
    private val scope: CoroutineScope
) {
    operator fun invoke() {
        scope.launch {
            repository.clearTempCache()
        }
    }
}
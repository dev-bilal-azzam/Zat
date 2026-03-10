package com.devbilal.domain.usecase.settings

import com.devbilal.domain.repository.SettingsRepository

class OnboardingDoneUseCase(
    private val repository: SettingsRepository
) {
    suspend fun isOnboardingDone() = repository.isOnboardingDone()

    suspend fun setIsOnboardingDone() = repository.setIsOnboardingDone()
}
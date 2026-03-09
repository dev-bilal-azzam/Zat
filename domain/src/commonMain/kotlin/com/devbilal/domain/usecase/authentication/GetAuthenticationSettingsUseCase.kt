package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.model.AuthenticationSettings
import com.devbilal.domain.repository.AuthenticationRepository

class GetAuthenticationSettingsUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(): AuthenticationSettings = repository.getAuthenticationSettings()
}
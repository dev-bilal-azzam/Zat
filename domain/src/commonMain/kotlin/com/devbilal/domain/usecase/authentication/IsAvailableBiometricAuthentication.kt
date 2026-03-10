package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.model.*
import com.devbilal.domain.repository.AuthenticationRepository

class IsAvailableBiometricAuthentication(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        val settings = repository.getAuthenticationSettings()

        return settings.primaryMethod != AuthenticationMethod.None &&
                settings.isBiometricAuthEnabled
    }
}
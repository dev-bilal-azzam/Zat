package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.model.*
import com.devbilal.domain.repository.AuthenticationRepository

class IsAvailableBiometricAuthentication (
private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(type: BiometricAuthenticationMethod): Boolean {
        val settings = repository.getAuthenticationSettings()

        return settings.primaryMethod != PrimaryAuthenticationMethod.None &&
                settings.biometricMethods.contains(type)
    }
}
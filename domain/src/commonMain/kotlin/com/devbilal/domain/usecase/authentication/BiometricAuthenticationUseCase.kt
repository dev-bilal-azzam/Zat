package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.repository.AuthenticationRepository

class BiometricAuthenticationUseCase(
    private val repository: AuthenticationRepository
) {
    suspend fun enable() = repository.enableBiometricAuthentication()

    suspend fun disable() = repository.disableBiometricAuthentication()
}
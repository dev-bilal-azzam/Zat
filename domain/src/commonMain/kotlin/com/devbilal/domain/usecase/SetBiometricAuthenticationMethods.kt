package com.devbilal.domain.usecase

import com.devbilal.domain.exception.FailedToSetBiometricAuthenticationException
import com.devbilal.domain.model.BiometricAuthenticationMethod
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.domain.repository.AuthenticationRepository

class SetBiometricAuthenticationMethods(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(
        biometricMethods: Set<BiometricAuthenticationMethod>
    ) {
        val current = repository.getAuthenticationSettings()

        if (current.primaryMethod != PrimaryAuthenticationMethod.None)
            throw FailedToSetBiometricAuthenticationException(
                "Biometric requires primary security"
            )

        val updated = current.copy(
            biometricMethods = biometricMethods
        )

        repository.saveAuthenticationSettings(updated)
    }
}
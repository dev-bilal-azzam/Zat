package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.exception.FailedToSetBiometricAuthenticationException
import com.devbilal.domain.model.BiometricAuthenticationMethod
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.domain.repository.AuthenticationRepository

class AddBiometricAuthenticationMethod(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(
        biometricMethod: BiometricAuthenticationMethod
    ) {
        val current = repository.getAuthenticationSettings()

        if (current.primaryMethod != PrimaryAuthenticationMethod.None)
            throw FailedToSetBiometricAuthenticationException(
                "Biometric requires primary security"
            )

        val updated = current.copy(
            biometricMethods = current.biometricMethods + biometricMethod
        )

        repository.saveAuthenticationSettings(updated)
    }
}
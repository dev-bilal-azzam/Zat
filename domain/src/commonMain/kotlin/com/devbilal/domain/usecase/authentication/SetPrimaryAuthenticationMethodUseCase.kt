package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.exception.FailedToSetAuthenticationMethodException
import com.devbilal.domain.model.*
import com.devbilal.domain.repository.AuthenticationRepository

class SetPrimaryAuthenticationMethodUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(
        method: PrimaryAuthenticationMethod,
    ) {
        val current = repository.getAuthenticationSettings()

        val newSettings = when (method) {
            is PrimaryAuthenticationMethod.None -> {
                AuthenticationSettings(
                    primaryMethod = PrimaryAuthenticationMethod.None,
                    biometricMethods = emptySet()
                )
            }

            is PrimaryAuthenticationMethod.Pin -> {
                if (method.code.isNullOrEmpty())
                    throw FailedToSetAuthenticationMethodException(
                        "Pin authentication method must have a value"
                    )

                AuthenticationSettings(
                    primaryMethod = method,
                    biometricMethods = current.biometricMethods
                )
            }

            is PrimaryAuthenticationMethod.Pattern -> {
                if (method.pattern.isNullOrEmpty())
                    throw FailedToSetAuthenticationMethodException(
                        "Pattern authentication method must have a value"
                    )

                AuthenticationSettings(
                    primaryMethod = method,
                    biometricMethods = current.biometricMethods
                )
            }
        }

        repository.saveAuthenticationSettings(newSettings)
    }
}

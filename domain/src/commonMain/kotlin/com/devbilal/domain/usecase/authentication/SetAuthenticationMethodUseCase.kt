package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.exception.FailedToSetAuthenticationMethodException
import com.devbilal.domain.model.*
import com.devbilal.domain.repository.AuthenticationRepository

class SetAuthenticationMethodUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(
        method: AuthenticationMethod,
    ) {

        if (method is AuthenticationMethod.Pin && method.code.isNullOrEmpty()) {
            throw FailedToSetAuthenticationMethodException(
                "Pin authentication method must have a value"
            )
        }

        if (method is AuthenticationMethod.Pattern && method.pattern.isNullOrEmpty()) {
            throw FailedToSetAuthenticationMethodException(
                "Pattern authentication method must have a value"
            )
        }

        repository.saveAuthenticationMethod(method)
    }
}

package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.domain.repository.AuthenticationRepository

class AuthenticateWithPrimaryMethodUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(method: PrimaryAuthenticationMethod) {
        repository.authenticateWithPrimaryMethod(method)
    }
}

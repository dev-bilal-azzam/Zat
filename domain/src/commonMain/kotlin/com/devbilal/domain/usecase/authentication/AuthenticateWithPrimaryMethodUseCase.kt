package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.repository.AuthenticationRepository

class AuthenticateWithPrimaryMethodUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(method: AuthenticationMethod) {
        repository.authenticateWithPrimaryMethod(method)
    }
}

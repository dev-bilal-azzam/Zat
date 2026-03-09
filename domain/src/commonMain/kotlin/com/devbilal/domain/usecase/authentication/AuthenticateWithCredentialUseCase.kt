package com.devbilal.domain.usecase.authentication

import com.devbilal.domain.repository.AuthenticationRepository

class AuthenticateWithCredentialUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(credential: String) {
        repository.authenticateWithCredential(credential)
    }
}
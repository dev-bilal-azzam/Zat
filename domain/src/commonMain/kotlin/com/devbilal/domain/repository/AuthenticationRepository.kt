package com.devbilal.domain.repository

import com.devbilal.domain.model.AuthenticationSettings

interface AuthenticationRepository {
    suspend fun saveAuthenticationSettings(settings: AuthenticationSettings)

    suspend fun getAuthenticationSettings(): AuthenticationSettings

    suspend fun authenticateWithCredential(credential: String): Boolean
}
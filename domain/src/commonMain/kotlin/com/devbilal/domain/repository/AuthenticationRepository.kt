package com.devbilal.domain.repository

import com.devbilal.domain.model.AuthenticationSettings
import com.devbilal.domain.model.PrimaryAuthenticationMethod

interface AuthenticationRepository {
    suspend fun saveAuthenticationSettings(settings: AuthenticationSettings)

    suspend fun getAuthenticationSettings(): AuthenticationSettings

    suspend fun authenticateWithPrimaryMethod(method: PrimaryAuthenticationMethod)
}

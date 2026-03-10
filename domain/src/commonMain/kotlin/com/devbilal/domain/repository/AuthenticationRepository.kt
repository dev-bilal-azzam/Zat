package com.devbilal.domain.repository

import com.devbilal.domain.model.AuthenticationSettings
import com.devbilal.domain.model.AuthenticationMethod

interface AuthenticationRepository {
    suspend fun saveAuthenticationMethod(method: AuthenticationMethod)

    suspend fun getAuthenticationSettings(): AuthenticationSettings

    suspend fun authenticateWithPrimaryMethod(method: AuthenticationMethod)

    suspend fun enableBiometricAuthentication()

    suspend fun disableBiometricAuthentication()
}

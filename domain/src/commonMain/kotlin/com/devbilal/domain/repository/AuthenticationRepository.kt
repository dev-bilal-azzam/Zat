package com.devbilal.domain.repository

import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.model.AuthenticationSettings

interface AuthenticationRepository {
    suspend fun saveAuthenticationMethod(method: AuthenticationMethod)

    suspend fun getAuthenticationSettings(): AuthenticationSettings

    suspend fun authenticateWithPrimaryMethod(method: AuthenticationMethod)

    suspend fun enableBiometricAuthentication()

    suspend fun disableBiometricAuthentication()
}

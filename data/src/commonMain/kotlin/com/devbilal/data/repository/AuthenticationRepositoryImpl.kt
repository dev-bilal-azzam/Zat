package com.devbilal.data.repository

import com.devbilal.data.datasource.local.setting.*
import com.devbilal.data.datasource.local.setting.mapper.*
import com.devbilal.data.hash.Hasher
import com.devbilal.data.utils.safeCall
import com.devbilal.domain.exception.InvalidCredentialException
import com.devbilal.domain.model.*
import com.devbilal.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val settingsStorage: SettingsStorage,
    private val hasher: Hasher
) : AuthenticationRepository {
    override suspend fun saveAuthenticationSettings(settings: AuthenticationSettings) = safeCall {

        settingsStorage.primaryAuthenticationMethod = settings.primaryMethod.toDto()
        settingsStorage.biometricAuthenticationMethod = settings.biometricMethods

        val credential = when (val primaryMethod = settings.primaryMethod) {
            is PrimaryAuthenticationMethod.Pin -> primaryMethod.value
            is PrimaryAuthenticationMethod.Pattern -> primaryMethod.value
            else -> null
        }

        settingsStorage.credential = if (credential != null) hasher.hash(credential) else null
    }

    override suspend fun getAuthenticationSettings() = safeCall {
        AuthenticationSettings(
            primaryMethod = settingsStorage.primaryAuthenticationMethod.toDomain(),
            biometricMethods = settingsStorage.biometricAuthenticationMethod
        )
    }

    override suspend fun authenticateWithCredential(credential: String) = safeCall {
        val hashedCredential =
            settingsStorage.credential ?: throw InvalidCredentialException("Credential Not Found!")

        if (!hasher.verify(credential, hashedCredential))
            throw InvalidCredentialException("Credential Not Matched!")
    }


}
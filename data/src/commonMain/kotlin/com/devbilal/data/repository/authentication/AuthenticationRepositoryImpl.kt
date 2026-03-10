package com.devbilal.data.repository.authentication

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.datasource.local.setting.isBiometricAuthEnabled
import com.devbilal.data.datasource.local.setting.mapper.toDomain
import com.devbilal.data.datasource.local.setting.primaryAuthenticationMethod
import com.devbilal.data.utils.safeCall
import com.devbilal.domain.model.AuthenticationSettings
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val settingsStorage: SettingsStorage,
    private val strategyFactory: AuthenticationStrategyFactory
) : AuthenticationRepository {

    override suspend fun saveAuthenticationMethod(method: AuthenticationMethod) =
        safeCall {
            strategyFactory.create(method).save()
        }

    override suspend fun getAuthenticationSettings() = safeCall {
        AuthenticationSettings(
            method = settingsStorage.primaryAuthenticationMethod.toDomain(),
            isBiometricAuthEnabled = settingsStorage.isBiometricAuthEnabled
        )
    }

    override suspend fun authenticateWithPrimaryMethod(method: AuthenticationMethod) =
        safeCall {
            strategyFactory.create(method).authenticate()
        }

    override suspend fun enableBiometricAuthentication() {
        settingsStorage.isBiometricAuthEnabled = true
    }

    override suspend fun disableBiometricAuthentication() {
        settingsStorage.isBiometricAuthEnabled = false
    }
}

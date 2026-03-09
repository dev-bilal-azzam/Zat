package com.devbilal.data.repository.authentication

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.datasource.local.setting.biometricAuthenticationMethod
import com.devbilal.data.datasource.local.setting.mapper.toDomain
import com.devbilal.data.datasource.local.setting.primaryAuthenticationMethod
import com.devbilal.data.utils.safeCall
import com.devbilal.domain.model.AuthenticationSettings
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val settingsStorage: SettingsStorage,
    private val strategyFactory: AuthenticationStrategyFactory
) : AuthenticationRepository {

    override suspend fun saveAuthenticationSettings(settings: AuthenticationSettings) = safeCall {
        strategyFactory.create(settings.primaryMethod).save()
        settingsStorage.biometricAuthenticationMethod = settings.biometricMethods

    }

    override suspend fun getAuthenticationSettings() = safeCall {
        AuthenticationSettings(
            primaryMethod = settingsStorage.primaryAuthenticationMethod.toDomain(),
            biometricMethods = settingsStorage.biometricAuthenticationMethod
        )
    }

    override suspend fun authenticateWithPrimaryMethod(method: PrimaryAuthenticationMethod) =
        safeCall {
            strategyFactory.create(method).authenticate()
        }
}

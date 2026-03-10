package com.devbilal.data.repository.authentication

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.hash.Hasher
import com.devbilal.domain.model.AuthenticationMethod


class AuthenticationStrategyFactory(
    private val storage: SettingsStorage,
    private val hasher: Hasher
) {
    fun create(method: AuthenticationMethod): AuthenticationStrategy {
        return when (method) {
            is AuthenticationMethod.Pin -> PinAuthenticationStrategy(method, storage, hasher)
            is AuthenticationMethod.Pattern -> PatternAuthenticationStrategy(method, storage, hasher)
            AuthenticationMethod.None -> NoneAuthenticationStrategy(storage)
        }
    }
}

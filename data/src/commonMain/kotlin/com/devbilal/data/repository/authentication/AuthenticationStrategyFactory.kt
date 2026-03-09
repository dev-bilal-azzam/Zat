package com.devbilal.data.repository.authentication

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.hash.Hasher
import com.devbilal.domain.model.PrimaryAuthenticationMethod


class AuthenticationStrategyFactory(
    private val storage: SettingsStorage,
    private val hasher: Hasher
) {
    fun create(method: PrimaryAuthenticationMethod): AuthenticationStrategy {
        return when (method) {
            is PrimaryAuthenticationMethod.Pin -> PinAuthenticationStrategy(method, storage, hasher)
            is PrimaryAuthenticationMethod.Pattern -> PatternAuthenticationStrategy(method, storage, hasher)
            PrimaryAuthenticationMethod.None -> NoneAuthenticationStrategy(storage)
        }
    }
}

package com.devbilal.data.repository.authentication

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.datasource.local.setting.mapper.toDto
import com.devbilal.data.datasource.local.setting.patternCredential
import com.devbilal.data.datasource.local.setting.pinCredential
import com.devbilal.data.datasource.local.setting.primaryAuthenticationMethod
import com.devbilal.data.hash.Hasher
import com.devbilal.domain.exception.InvalidCredentialException
import com.devbilal.domain.model.AuthenticationMethod


interface AuthenticationStrategy {
    fun save()
    fun authenticate()
}

class PinAuthenticationStrategy(
    private val method: AuthenticationMethod.Pin,
    private val storage: SettingsStorage,
    private val hasher: Hasher
) : AuthenticationStrategy {
    override fun save() {
        storage.primaryAuthenticationMethod = method.toDto()
        storage.pinCredential = method.code?.let { hasher.hash(it) }
    }

    override fun authenticate() {
        val hashedValue = storage.pinCredential
        val rawValue = method.code

        if (hashedValue == null || rawValue == null || !hasher.verify(rawValue, hashedValue)) {
            throw InvalidCredentialException("Credential Not Matched!")
        }
    }
}

class PatternAuthenticationStrategy(
    private val method: AuthenticationMethod.Pattern,
    private val storage: SettingsStorage,
    private val hasher: Hasher
) : AuthenticationStrategy {
    override fun save() {
        storage.primaryAuthenticationMethod = method.toDto()
        storage.patternCredential = method.pattern?.joinToString(",")?.let { hasher.hash(it) }
    }

    override fun authenticate() {
        val hashedValue = storage.patternCredential
        val rawValue = method.pattern?.joinToString(",")

        if (hashedValue == null || rawValue == null || !hasher.verify(rawValue, hashedValue)) {
            throw InvalidCredentialException("Credential Not Matched!")
        }
    }
}

class NoneAuthenticationStrategy(private val storage: SettingsStorage) : AuthenticationStrategy {
    override fun save() {
        storage.pinCredential = null
        storage.patternCredential = null
    }

    override fun authenticate() {}
}

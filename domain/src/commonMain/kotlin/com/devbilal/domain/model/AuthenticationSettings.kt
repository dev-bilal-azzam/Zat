package com.devbilal.domain.model

data class AuthenticationSettings(
    val method: AuthenticationMethod,
    val isBiometricAuthEnabled: Boolean
)
package com.devbilal.domain.model

data class AuthenticationSettings(
    val primaryMethod: AuthenticationMethod,
    val isBiometricAuthEnabled: Boolean
)
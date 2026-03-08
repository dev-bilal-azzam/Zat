package com.devbilal.domain.model

data class AuthenticationSettings(
    val primaryMethod: PrimaryAuthenticationMethod,
    val biometricMethods: Set<BiometricAuthenticationMethod> = emptySet()
)
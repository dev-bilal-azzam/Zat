package com.devbilal.domain.model

sealed class PrimaryAuthenticationMethod {
    object None : PrimaryAuthenticationMethod()
    data class Pin(val value: String?) : PrimaryAuthenticationMethod()
    data class Pattern(val value: String?) : PrimaryAuthenticationMethod()
}

enum class BiometricAuthenticationMethod {
    FACE,
    FINGERPRINT
}
package com.devbilal.domain.model

sealed class PrimaryAuthenticationMethod {
    data object None : PrimaryAuthenticationMethod()
    data class Pin(val code: String?) : PrimaryAuthenticationMethod()
    data class Pattern(val pattern: List<Int>?) : PrimaryAuthenticationMethod()
}

enum class BiometricAuthenticationMethod {
    FACE,
    FINGERPRINT
}

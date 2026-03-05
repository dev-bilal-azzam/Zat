package com.devbilal.domain.model

sealed class PrimaryAuthenticationMethod {
    object None : PrimaryAuthenticationMethod() {
        override fun toString(): String {
            return "None"
        }
    }
    data class Pin(val value: String?) : PrimaryAuthenticationMethod() {
        override fun toString(): String {
            return "Pin"
        }
    }
    data class Pattern(val value: String?) : PrimaryAuthenticationMethod() {
        override fun toString(): String {
            return "Pattern"
        }
    }
}

enum class BiometricAuthenticationMethod {
    FACE,
    FINGERPRINT
}
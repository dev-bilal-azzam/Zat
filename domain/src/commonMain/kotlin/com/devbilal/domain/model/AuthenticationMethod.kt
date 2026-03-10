package com.devbilal.domain.model

sealed class AuthenticationMethod {
    data object None : AuthenticationMethod()
    data class Pin(val code: String?) : AuthenticationMethod()
    data class Pattern(val pattern: List<Int>?) : AuthenticationMethod()
}
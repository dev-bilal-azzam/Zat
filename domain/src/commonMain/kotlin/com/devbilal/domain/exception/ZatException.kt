package com.devbilal.domain.exception

open class ZatException(override val message: String): Exception(message)

class UnknownException(override val message: String = "Unknown Error"): ZatException(message)

class InvalidCredentialException(override val message: String): ZatException(message)

class FailedToSetBiometricAuthenticationException(override val message: String): ZatException(message)

class FailedToSetAuthenticationMethodException(override val message: String): ZatException(message)
package com.devbilal.data.datasource.local.setting.mapper

import com.devbilal.data.datasource.local.setting.PrimaryAuthenticationMethodDto
import com.devbilal.domain.model.AuthenticationMethod

fun PrimaryAuthenticationMethodDto.toDomain(): AuthenticationMethod = when (this) {
    PrimaryAuthenticationMethodDto.None -> AuthenticationMethod.None
    PrimaryAuthenticationMethodDto.Pattern -> AuthenticationMethod.Pattern(null)
    PrimaryAuthenticationMethodDto.Pin -> AuthenticationMethod.Pin(null)
}


fun AuthenticationMethod.toDto(): PrimaryAuthenticationMethodDto = when (this) {
    AuthenticationMethod.None -> PrimaryAuthenticationMethodDto.None
    is AuthenticationMethod.Pattern -> PrimaryAuthenticationMethodDto.Pattern
    is AuthenticationMethod.Pin -> PrimaryAuthenticationMethodDto.Pin
}
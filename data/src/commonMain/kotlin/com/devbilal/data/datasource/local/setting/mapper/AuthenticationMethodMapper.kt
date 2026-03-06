package com.devbilal.data.datasource.local.setting.mapper

import com.devbilal.data.datasource.local.setting.PrimaryAuthenticationMethodDto
import com.devbilal.domain.model.PrimaryAuthenticationMethod

fun PrimaryAuthenticationMethodDto.toDomain(): PrimaryAuthenticationMethod = when (this) {
    PrimaryAuthenticationMethodDto.None -> PrimaryAuthenticationMethod.None
    PrimaryAuthenticationMethodDto.Pattern -> PrimaryAuthenticationMethod.Pattern(null)
    PrimaryAuthenticationMethodDto.Pin -> PrimaryAuthenticationMethod.Pin(null)
}


fun PrimaryAuthenticationMethod.toDto(): PrimaryAuthenticationMethodDto = when (this) {
    PrimaryAuthenticationMethod.None -> PrimaryAuthenticationMethodDto.None
    is PrimaryAuthenticationMethod.Pattern -> PrimaryAuthenticationMethodDto.Pattern
    is PrimaryAuthenticationMethod.Pin -> PrimaryAuthenticationMethodDto.Pin
}
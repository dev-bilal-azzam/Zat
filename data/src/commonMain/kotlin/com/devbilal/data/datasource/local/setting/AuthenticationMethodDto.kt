package com.devbilal.data.datasource.local.setting

import kotlinx.serialization.Serializable

@Serializable
sealed class PrimaryAuthenticationMethodDto {
    object None : PrimaryAuthenticationMethodDto()
    object Pin : PrimaryAuthenticationMethodDto()
    object Pattern : PrimaryAuthenticationMethodDto()
}

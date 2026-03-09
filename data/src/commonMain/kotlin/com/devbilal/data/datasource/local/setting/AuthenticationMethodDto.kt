package com.devbilal.data.datasource.local.setting

import kotlinx.serialization.Serializable

@Serializable
sealed class PrimaryAuthenticationMethodDto {

    @Serializable
    object None : PrimaryAuthenticationMethodDto()

    @Serializable
    object Pin : PrimaryAuthenticationMethodDto()

    @Serializable
    object Pattern : PrimaryAuthenticationMethodDto()

}

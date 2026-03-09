package com.devbilal.presentation.common.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object Onboarding: Route, NavKey

    @Serializable
    data object InitSecurity: Route, NavKey

    @Serializable
    data object SetupPin: Route, NavKey


    @Serializable
    data object SetupPattern: Route, NavKey



    @Serializable
    data object InitBiometric: Route, NavKey

    @Serializable
    data object Unlock: Route, NavKey

    @Serializable
    data object Home: Route, NavKey
}
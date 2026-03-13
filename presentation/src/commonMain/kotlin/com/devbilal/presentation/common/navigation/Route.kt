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
    data class SetupPin(val onSuccessfulSetup: (() -> Unit)? = null): Route, NavKey


    @Serializable
    data class SetupPattern(val onSuccessfulSetup: (() -> Unit)? = null): Route, NavKey



    @Serializable
    data object InitBiometric: Route, NavKey

    @Serializable
    data class Unlock(
        val title: String? = null,
        val description: String? = null,
        val onSuccessfulUnlock: (() -> Unit)? = null
    ) : Route, NavKey

    @Serializable
    data object Home: Route, NavKey

    @Serializable
    data object Search: Route, NavKey

    @Serializable
    data object Settings: Route, NavKey

    @Serializable
    data object Calendar: Route, NavKey

    @Serializable
    data object AddEditDiary: Route, NavKey

    @Serializable
    data object Security: Route, NavKey

}
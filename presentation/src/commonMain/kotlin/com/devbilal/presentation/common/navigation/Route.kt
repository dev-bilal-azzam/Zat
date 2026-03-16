package com.devbilal.presentation.common.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    val disposable: Boolean
        get() = false


    @Serializable
    data object Onboarding: Route, NavKey

    @Serializable
    data object InitSecurity: Route, NavKey

    @Serializable
    data object InitBiometric: Route, NavKey

    @Serializable
    data object Home: Route, NavKey

    @Serializable
    data object Search: Route, NavKey

    @Serializable
    data object Settings: Route, NavKey

    @Serializable
    data object Calendar: Route, NavKey

    @Serializable
    data class AddEditDiary(val entryId: String? = null): Route, NavKey

    @Serializable
    data object Security: Route, NavKey

}


abstract class DisposableRoute: Route {
    final override val disposable = true

    @Serializable
    data class SetupPin(val onSuccessfulSetup: (() -> Unit)? = null): DisposableRoute()


    @Serializable
    data class SetupPattern(val onSuccessfulSetup: (() -> Unit)? = null): DisposableRoute()


    @Serializable
    data class Unlock(
        val title: String? = null,
        val description: String? = null,
        val onSuccessfulUnlock: (() -> Unit)? = null
    ) : DisposableRoute()

}

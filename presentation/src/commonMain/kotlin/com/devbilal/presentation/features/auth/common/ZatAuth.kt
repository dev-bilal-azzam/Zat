package com.devbilal.presentation.features.auth.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.devbilal.presentation.common.navigation.DisposableRoute
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.common.navigation.Navigator
import com.devbilal.presentation.common.navigation.Route
import com.devbilal.presentation.common.navigation.rememberNavigationState
import com.devbilal.presentation.features.auth.common.navigation.AuthNavDisplay
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun ZatAuth(
    isOnboardingDone: Boolean,
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit
) {
    val startRoute = if (isOnboardingDone) {
        DisposableRoute.Unlock()
    } else {
        Route.Onboarding
    }

    val topLevelRoutes = setOf(
        Route.Onboarding,
        Route.InitSecurity,
        Route.InitBiometric,
        DisposableRoute.Unlock()
    )

    val configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Onboarding::class, Route.Onboarding.serializer())
                    subclass(Route.InitSecurity::class, Route.InitSecurity.serializer())
                    subclass(DisposableRoute.SetupPattern::class, DisposableRoute.SetupPattern.serializer())
                    subclass(DisposableRoute.SetupPin::class, DisposableRoute.SetupPin.serializer())
                    subclass(Route.InitBiometric::class, Route.InitBiometric.serializer())
                    subclass(DisposableRoute.Unlock::class, DisposableRoute.Unlock.serializer())
                }
            }
    }

    val navigationState = rememberNavigationState(
        startRoute = startRoute,
        topLevelRoutes = topLevelRoutes,
        configuration = configuration
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    CompositionLocalProvider(
        LocalNavigator provides navigator,
    ) {
        AuthNavDisplay(
            modifier = modifier,
            navigator = navigator,
            navigateHome = navigateHome
        )

    }
}


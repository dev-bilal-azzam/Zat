package com.devbilal.presentation.features.auth.common

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.devbilal.presentation.common.navigation.Route
import com.devbilal.presentation.features.auth.screens.initbiometric.InitBiometricScreen
import com.devbilal.presentation.features.auth.screens.initsecurity.InitSecurityScreen
import com.devbilal.presentation.features.auth.screens.onboarding.OnBoardingScreen
import com.devbilal.presentation.features.auth.screens.setuppattern.SetupPatternScreen
import com.devbilal.presentation.features.auth.screens.setuppin.SetupPinScreen
import com.devbilal.presentation.features.auth.screens.unlock.UnlockScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


val LocalBackStack = staticCompositionLocalOf<NavBackStack<NavKey>> {
    error("No NavController provided")
}


@Composable
fun ZatAuth(
    isOnboardingDone: Boolean,
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit
) {
    val startRoute = if (isOnboardingDone) {
        Route.Unlock()
    } else {
        Route.Onboarding
    }

    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Onboarding::class, Route.Onboarding.serializer())
                    subclass(Route.InitSecurity::class, Route.InitSecurity.serializer())
                    subclass(Route.SetupPattern::class, Route.SetupPattern.serializer())
                    subclass(Route.SetupPin::class, Route.SetupPin.serializer())
                    subclass(Route.InitBiometric::class, Route.InitBiometric.serializer())
                    subclass(Route.Unlock::class, Route.Unlock.serializer())
                }
            }
        },
        startRoute
    )
    CompositionLocalProvider(
        LocalBackStack provides backStack,
    ) {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
            },
            popTransitionSpec = {
                slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
            },
            predictivePopTransitionSpec = {
                slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
            },
            entryProvider = entryProvider {
                entry<Route.Onboarding> {
                    OnBoardingScreen()
                }

                entry<Route.InitSecurity> {
                    InitSecurityScreen(
                        navigateHome = navigateHome
                    )
                }

                entry<Route.SetupPin> {
                    SetupPinScreen(
                        onSuccessfulSetup = { backStack.navigateToInitBiometricWithReplaceAll() }
                    )
                }

                entry<Route.SetupPattern> {
                    SetupPatternScreen(
                        onSuccessfulSetup = { backStack.navigateToInitBiometricWithReplaceAll() }
                    )
                }

                entry<Route.InitBiometric> {
                    InitBiometricScreen(
                        navigateHome = navigateHome
                    )
                }

                entry<Route.Unlock> {
                    UnlockScreen(
                        onSuccessfulUnlock = navigateHome,
                    )
                }

            }
        )
    }
}


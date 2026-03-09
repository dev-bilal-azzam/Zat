package com.devbilal.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.presentation.features.home.HomeScreen
import com.devbilal.presentation.features.initsecurity.InitSecurityScreen
import com.devbilal.presentation.features.onboarding.OnBoardingScreen
import com.devbilal.presentation.features.setuppattern.SetupPatternScreen
import com.devbilal.presentation.features.setuppin.SetupPinScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier.Companion
) {
    val startRoute = Route.Onboarding
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Onboarding::class, Route.Onboarding.serializer())
                    subclass(Route.InitSecurity::class, Route.InitSecurity.serializer())
                }
            }
        },
        startRoute
    )
    NavDisplay(
        backStack = backStack,
        entryProvider = { key ->
            when (key) {
                is Route.Onboarding -> {
                    NavEntry(key) {
                        OnBoardingScreen(
                            onNavigateToInitSecurity = {
                                backStack.add(Route.InitSecurity)
                            }
                        )
                    }
                }

                is Route.InitSecurity -> {
                    // to access arguments -> key.firstArgument
                    NavEntry(key) {
                        InitSecurityScreen(
                            onNavigateBack = { backStack.removeLastOrNull() },
                            onNavigateToHome = { backStack.add(Route.Home) },
                            onNavigateToSetupPin = { backStack.add(Route.SetupPin) },
                            onNavigateToSetupPattern = { backStack.add(Route.SetupPattern) }
                        )
                    }
                }

                is Route.SetupPin -> {
                    NavEntry(key) {
                        SetupPinScreen(
                            onNavigateBack = { backStack.removeLastOrNull() },
                            onNavigateToHome = { backStack.add(Route.Home) }
                        )
                    }
                }

                is Route.SetupPattern -> {
                    NavEntry(key) {
                        SetupPatternScreen(
                            onNavigateBack = { backStack.removeLastOrNull() },
                            onNavigateToHome = { backStack.add(Route.Home) }
                        )
                    }
                }

                is Route.Home -> {
                    NavEntry(key) {
                        HomeScreen()
                    }
                }

                else -> error("Unknown NavKey $key")
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview
fun PreviewNavigationRoot() {
    ZatTheme {
        NavigationRoot()
    }
}
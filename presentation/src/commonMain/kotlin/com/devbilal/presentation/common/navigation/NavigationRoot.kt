package com.devbilal.presentation.common.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.snackbar.AnimatedSnackBarHost
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.features.home.HomeScreen
import com.devbilal.presentation.features.initbiometric.InitBiometricScreen
import com.devbilal.presentation.features.initsecurity.InitSecurityScreen
import com.devbilal.presentation.features.onboarding.OnBoardingScreen
import com.devbilal.presentation.features.setuppattern.SetupPatternScreen
import com.devbilal.presentation.features.setuppin.SetupPinScreen
import com.devbilal.presentation.features.unlock.UnlockScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.zat
import zat.presentation.generated.resources.zat_logo_with_name

val LocalBackStack = staticCompositionLocalOf<NavBackStack<NavKey>> {
    error("No NavController provided")
}

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val state = viewModel.collectState()

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.surfaceLow),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.zat_logo_with_name),
                contentDescription = stringResource(Res.string.zat),
                modifier = Modifier.size(160.dp)
            )
        }
        return
    }

    val startRoute = if (state.isOnboardingDone == false) {
        Route.Onboarding
    } else if (state.authenticationMethod is AuthenticationMethod.None) {
        Route.Home
    } else {
        Route.Unlock
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
                    subclass(Route.Home::class, Route.Home.serializer())
                }
            }
        },
        startRoute
    )

    Box(modifier = modifier.fillMaxSize()) {
        CompositionLocalProvider(
            LocalBackStack provides backStack,
        ) {
            NavDisplay(
                modifier = Modifier.fillMaxSize(),
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
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),

                entryProvider = { key ->
                    when (key) {
                        is Route.Onboarding -> {
                            NavEntry(key) {
                                OnBoardingScreen()
                            }
                        }

                        is Route.InitSecurity -> {
                            // to access arguments -> key.firstArgument
                            NavEntry(key) {
                                InitSecurityScreen()
                            }
                        }

                        is Route.SetupPin -> {
                            NavEntry(key) {
                                SetupPinScreen()
                            }
                        }

                        is Route.SetupPattern -> {
                            NavEntry(key) {
                                SetupPatternScreen()
                            }
                        }

                        is Route.InitBiometric -> {
                            NavEntry(key) {
                                InitBiometricScreen()
                            }
                        }

                        is Route.Home -> {
                            NavEntry(key) {
                                HomeScreen()
                            }
                        }

                        is Route.Unlock -> {
                            NavEntry(key) {
                                UnlockScreen()
                            }
                        }

                        else -> error("Unknown NavKey $key")
                    }
                }
            )
        }

        Box(
            modifier = Modifier.fillMaxSize().statusBarsPadding()
                .padding(horizontal = Theme.spacing._16),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedSnackBarHost(LocalSnackBarHostController.current)
        }
    }
}

@Composable
@Preview
fun PreviewNavigationRoot() {
    ZatTheme {
        NavigationRoot()
    }
}

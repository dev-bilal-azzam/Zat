package com.devbilal.presentation.features.auth.common.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.devbilal.presentation.common.navigation.DisposableRoute
import com.devbilal.presentation.common.navigation.Route
import com.devbilal.presentation.features.auth.screens.initbiometric.InitBiometricScreen
import com.devbilal.presentation.features.auth.screens.initsecurity.InitSecurityScreen
import com.devbilal.presentation.features.auth.screens.onboarding.OnBoardingScreen
import com.devbilal.presentation.features.auth.screens.setuppattern.SetupPatternScreen
import com.devbilal.presentation.features.auth.screens.setuppin.SetupPinScreen
import com.devbilal.presentation.features.auth.screens.unlock.UnlockScreen
import com.devbilal.presentation.common.navigation.Navigator
import com.devbilal.presentation.common.navigation.toEntries

@Composable
fun AuthNavDisplay(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    navigateHome: () -> Unit
) {
    NavDisplay(
        modifier = modifier,
        onBack = navigator::navigateBack,
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
        entries = navigator.state.toEntries(
            entryProvider {
                entry<Route.Onboarding> {
                    OnBoardingScreen()
                }

                entry<Route.InitSecurity> {
                    InitSecurityScreen(
                        navigateHome = navigateHome
                    )
                }

                entry<DisposableRoute.SetupPin> {
                    SetupPinScreen(
                        onSuccessfulSetup = { navigator.replaceInitBiometric() }
                    )
                }

                entry<DisposableRoute.SetupPattern> {
                    SetupPatternScreen(
                        onSuccessfulSetup = { navigator.replaceInitBiometric() }
                    )
                }

                entry<Route.InitBiometric> {
                    InitBiometricScreen(
                        navigateHome = navigateHome
                    )
                }

                entry<DisposableRoute.Unlock> {
                    UnlockScreen(
                        onSuccessfulUnlock = navigateHome,
                    )
                }


            }
        )
    )
}
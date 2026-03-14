package com.devbilal.presentation.features.auth.common.navigation

import com.devbilal.presentation.common.navigation.DisposableRoute
import com.devbilal.presentation.common.navigation.Navigator
import com.devbilal.presentation.common.navigation.Route

internal fun Navigator.navigateToSetupPin(onSuccessfulSetup: (() -> Unit)? = null) =
    navigate(DisposableRoute.SetupPin(onSuccessfulSetup))

internal fun Navigator.navigateToSetupPattern(onSuccessfulSetup: (() -> Unit)? = null) =
    navigate(DisposableRoute.SetupPattern(onSuccessfulSetup))

internal fun Navigator.navigateToInitSecurity() = navigate(Route.InitSecurity)

internal fun Navigator.replaceInitBiometric() = replace(Route.InitBiometric)
package com.devbilal.presentation.common.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

internal fun NavBackStack<NavKey>.navigateBack() = removeLastOrNull()

internal fun NavBackStack<NavKey>.navigateToHome() = add(Route.Home)

internal fun NavBackStack<NavKey>.navigateToSetupPin() = add(Route.SetupPin)

internal fun NavBackStack<NavKey>.navigateToSetupPattern() = add(Route.SetupPattern)

internal fun NavBackStack<NavKey>.navigateToInitSecurity() = add(Route.InitSecurity)

internal fun NavBackStack<NavKey>.navigateToInitBiometric() = add(Route.InitBiometric)

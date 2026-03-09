package com.devbilal.presentation.common.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

internal fun NavBackStack<NavKey>.push(route: NavKey) = add(route)

internal fun NavBackStack<NavKey>.replace(route: NavKey) {
    removeLastOrNull()
    add(route)
}

internal fun NavBackStack<NavKey>.replaceAll(route: NavKey) {
    removeAll { true }
    add(route)
}


internal fun NavBackStack<NavKey>.navigateBack() = removeLastOrNull()

internal fun NavBackStack<NavKey>.navigateToHome() = push(Route.Home)
internal fun NavBackStack<NavKey>.navigateToHomeWithReplace() = replace(Route.Home)
internal fun NavBackStack<NavKey>.navigateToHomeWithReplaceAll() = replaceAll(Route.Home)

internal fun NavBackStack<NavKey>.navigateToSetupPin() = push(Route.SetupPin)

internal fun NavBackStack<NavKey>.navigateToSetupPattern() = push(Route.SetupPattern)

internal fun NavBackStack<NavKey>.navigateToInitSecurity() = push(Route.InitSecurity)

internal fun NavBackStack<NavKey>.navigateToInitBiometric() = push(Route.InitBiometric)
internal fun NavBackStack<NavKey>.navigateToInitBiometricWithReplace() = replace(Route.InitBiometric)
internal fun NavBackStack<NavKey>.navigateToInitBiometricWithReplaceAll() = replaceAll(Route.InitBiometric)

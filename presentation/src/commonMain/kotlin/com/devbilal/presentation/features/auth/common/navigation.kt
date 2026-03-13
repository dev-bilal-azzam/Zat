package com.devbilal.presentation.features.auth.common

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.devbilal.presentation.common.navigation.Route

internal fun NavBackStack<NavKey>.push(route: NavKey) = add(route)

internal fun NavBackStack<NavKey>.replaceAll(route: NavKey) {
    removeAll { true }
    add(route)
}

internal fun NavBackStack<NavKey>.navigateBack() = removeLastOrNull()


internal fun NavBackStack<NavKey>.navigateToSetupPin() = push(Route.SetupPin())
internal fun NavBackStack<NavKey>.navigateToSetupPattern() = push(Route.SetupPattern())
internal fun NavBackStack<NavKey>.navigateToInitSecurity() = push(Route.InitSecurity)
internal fun NavBackStack<NavKey>.navigateToInitBiometricWithReplaceAll() = replaceAll(Route.InitBiometric)
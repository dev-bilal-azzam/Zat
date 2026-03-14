package com.devbilal.presentation.common.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavKey



val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No Navigator provided")
}


class Navigator(val state: NavigationState) {

    fun navigate(
        route: NavKey,
        options: NavOptions = NavOptions()
    ) {

        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
            return
        }

        val stack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")

        val last = stack.lastOrNull()

        // remove disposable screen
        if (last is Route && last.disposable) {
            stack.removeLastOrNull()
        }

        // popUpTo
        options.popUpTo?.let { target ->
            while (stack.isNotEmpty()) {
                val top = stack.last()

                if (top == target) {
                    if (options.inclusive) {
                        stack.removeLastOrNull()
                    }
                    break
                }

                stack.removeLastOrNull()
            }
        }

        // launchSingleTop
        if (options.singleTop && stack.lastOrNull() == route) {
            return
        }

        stack.add(route)
    }

    fun replace(route: NavKey) {
        if(route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            val currentStack = state.backStacks[state.topLevelRoute]
            currentStack?.clear()
            currentStack?.add(route)
        }
    }

    fun navigateBack() {

        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")

        val currentRoute = currentStack.last()

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
            return
        }

        currentStack.removeLastOrNull()

        // remove disposable routes
        while (true) {
            val last = currentStack.lastOrNull()

            if (last is Route && last.disposable) {
                currentStack.removeLastOrNull()
            } else {
                break
            }
        }
    }

    data class NavOptions(
        val singleTop: Boolean = true,
        val popUpTo: NavKey? = null,
        val inclusive: Boolean = false
    )
}
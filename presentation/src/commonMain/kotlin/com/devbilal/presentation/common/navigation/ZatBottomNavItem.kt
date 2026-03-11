package com.devbilal.presentation.common.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.*


data class BottomNavItem(
    val notSelectedIconRes: DrawableResource,
    val selectedIconRes: DrawableResource,
    val titleRes: StringResource,
)

val topLevelRoutes = mapOf(
    Route.Home to BottomNavItem (
        notSelectedIconRes = Res.drawable.ic_home,
        selectedIconRes = Res.drawable.ic_home_selected,
        titleRes = Res.string.home
    ),
    Route.Search to BottomNavItem (
        notSelectedIconRes = Res.drawable.ic_search,
        selectedIconRes = Res.drawable.ic_search_selected,
        titleRes = Res.string.search
    ),
    Route.Settings to BottomNavItem (
        notSelectedIconRes = Res.drawable.ic_settings,
        selectedIconRes = Res.drawable.ic_settings_selected,
        titleRes = Res.string.settings
    ),
    Route.Calendar to BottomNavItem (
        notSelectedIconRes = Res.drawable.ic_calendar,
        selectedIconRes = Res.drawable.ic_calendar_selected,
        titleRes = Res.string.calendar
    ),
)
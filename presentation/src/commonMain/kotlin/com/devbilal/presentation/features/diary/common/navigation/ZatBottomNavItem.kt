package com.devbilal.presentation.features.diary.common.navigation

import androidx.navigation3.runtime.NavKey
import com.devbilal.presentation.common.navigation.Route
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.calendar
import zat.presentation.generated.resources.home
import zat.presentation.generated.resources.ic_calendar
import zat.presentation.generated.resources.ic_calendar_selected
import zat.presentation.generated.resources.ic_home
import zat.presentation.generated.resources.ic_home_selected
import zat.presentation.generated.resources.ic_search
import zat.presentation.generated.resources.ic_search_selected
import zat.presentation.generated.resources.ic_settings
import zat.presentation.generated.resources.ic_settings_selected
import zat.presentation.generated.resources.search
import zat.presentation.generated.resources.settings


data class BottomNavItem(
    val notSelectedIconRes: DrawableResource,
    val selectedIconRes: DrawableResource,
    val titleRes: StringResource,
)

val diaryTopLevelRoutes = mapOf<NavKey, BottomNavItem>(
    Route.Home to BottomNavItem (
        notSelectedIconRes = Res.drawable.ic_home,
        selectedIconRes = Res.drawable.ic_home_selected,
        titleRes = Res.string.home
    ),
    Route.Calendar to BottomNavItem (
        notSelectedIconRes = Res.drawable.ic_calendar,
        selectedIconRes = Res.drawable.ic_calendar_selected,
        titleRes = Res.string.calendar
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
)
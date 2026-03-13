package com.devbilal.presentation.common.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.devbilal.presentation.features.addeditdiary.AddEditDiaryScreen
import com.devbilal.presentation.features.calendar.CalendarScreen
import com.devbilal.presentation.features.home.HomeScreen
import com.devbilal.presentation.features.search.SearchScreen
import com.devbilal.presentation.features.settings.SettingsScreen

@Composable
fun ZatNavDisplay(
    modifier: Modifier = Modifier,
    navigator: Navigator
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
                entry<Route.Home> {
                    HomeScreen()
                }

                entry<Route.Search> {
                    SearchScreen()
                }

                entry<Route.Settings> {
                    SettingsScreen()
                }

                entry<Route.Calendar> {
                    CalendarScreen()
                }

                entry<Route.AddEditDiary> {
                    AddEditDiaryScreen()
                }
            }
        )
    )
}
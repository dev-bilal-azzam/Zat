package com.devbilal.presentation.features.diary.common.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.devbilal.presentation.common.navigation.DisposableRoute
import com.devbilal.presentation.common.navigation.Navigator
import com.devbilal.presentation.common.navigation.Route
import com.devbilal.presentation.common.navigation.toEntries
import com.devbilal.presentation.features.auth.screens.setuppattern.SetupPatternScreen
import com.devbilal.presentation.features.auth.screens.setuppin.SetupPinScreen
import com.devbilal.presentation.features.auth.screens.unlock.UnlockScreen
import com.devbilal.presentation.features.diary.screens.addeditdiary.AddEditDiaryScreen
import com.devbilal.presentation.features.diary.screens.calendar.CalendarScreen
import com.devbilal.presentation.features.diary.screens.home.HomeScreen
import com.devbilal.presentation.features.diary.screens.search.SearchScreen
import com.devbilal.presentation.features.diary.screens.security.SecurityScreen
import com.devbilal.presentation.features.diary.screens.settings.SettingsScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DiaryNavDisplay(
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

                entry<Route.AddEditDiary> { route ->
                    AddEditDiaryScreen(
                        viewModel = koinViewModel { parametersOf(route) }
                    )
                }

                entry<Route.Security> {
                    SecurityScreen()
                }

                entry<DisposableRoute.Unlock> {
                    val title = it.title
                    val description = it.description
                    val onSuccessfulUnlock = it.onSuccessfulUnlock

                    UnlockScreen(
                        title = title,
                        description = description,
                        onSuccessfulUnlock = onSuccessfulUnlock ?: {}
                    )
                }

                entry<DisposableRoute.SetupPin> {
                    val onSuccessfulSetup = it.onSuccessfulSetup
                    SetupPinScreen(
                        onSuccessfulSetup = onSuccessfulSetup ?: {}
                    )
                }

                entry<DisposableRoute.SetupPattern> {
                    SetupPatternScreen(
                        onSuccessfulSetup = it.onSuccessfulSetup ?: {}
                    )
                }
            }
        )
    )
}
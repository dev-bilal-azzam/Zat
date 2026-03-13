package com.devbilal.presentation.features.diary.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.common.navigation.Route
import com.devbilal.presentation.features.diary.common.components.ZatNavBar
import com.devbilal.presentation.features.diary.common.navigation.DiaryNavDisplay
import com.devbilal.presentation.features.diary.common.navigation.LocalNavigator
import com.devbilal.presentation.features.diary.common.navigation.Navigator
import com.devbilal.presentation.features.diary.common.navigation.rememberNavigationState
import com.devbilal.presentation.features.diary.common.navigation.topLevelRoutes

@Composable
fun ZatDiary(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = topLevelRoutes.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val isBottomBarVisible = topLevelRoutes.keys
        .contains(navigationState.backStacks[navigationState.topLevelRoute]?.last())

    Scaffold(
        modifier = modifier,
        isBottomBarVisible = isBottomBarVisible,
        bottomBar = {
            ZatNavBar(
                selectedKey = navigationState.topLevelRoute,
                onSelectKey = {
                    navigator.navigate(it)
                }
            )
        }
    ) {
        CompositionLocalProvider(
            LocalNavigator provides navigator,
        ) {
            DiaryNavDisplay(navigator = navigator)
        }
    }

}


@Composable
@Preview
fun ZatDiaryPreview() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        ZatDiary()

    }
}
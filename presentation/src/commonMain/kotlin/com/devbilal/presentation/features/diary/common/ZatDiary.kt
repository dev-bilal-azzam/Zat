package com.devbilal.presentation.features.diary.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.common.navigation.Route
import com.devbilal.presentation.features.diary.common.components.ZatNavBar
import com.devbilal.presentation.features.diary.common.navigation.DiaryNavDisplay
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.common.navigation.Navigator
import com.devbilal.presentation.common.navigation.rememberNavigationState
import com.devbilal.presentation.features.diary.common.navigation.diaryTopLevelRoutes
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun ZatDiary(
    modifier: Modifier = Modifier
) {

    val topLevelNavKeys: Set<NavKey> = remember { diaryTopLevelRoutes.keys.toSet() }

    val serializersConfig = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(Route.Home::class, Route.Home.serializer())
                subclass(Route.Search::class, Route.Search.serializer())
                subclass(Route.Settings::class, Route.Settings.serializer())
                subclass(Route.Calendar::class, Route.Calendar.serializer())
                subclass(Route.AddEditDiary::class, Route.AddEditDiary.serializer())
                subclass(Route.Security::class, Route.Security.serializer())
            }
        }
    }

    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = topLevelNavKeys,
        configuration = serializersConfig
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val lastKeyOnStack: NavKey? = navigationState.backStacks[navigationState.topLevelRoute]?.lastOrNull()

    val isBottomBarVisible = topLevelNavKeys
        .contains(lastKeyOnStack)

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
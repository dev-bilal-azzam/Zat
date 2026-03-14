package com.devbilal.presentation.features.diary.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.devbilal.designsystem.component.bottomNavigation.BottomNavigationBar
import com.devbilal.presentation.features.diary.common.navigation.diaryTopLevelRoutes

@Composable
fun ZatNavBar(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigationBar(modifier = modifier) {
        diaryTopLevelRoutes.forEach { item ->
            bottomNavigationItem(
                notSelectedIcon = item.value.notSelectedIconRes,
                selectedIcon = item.value.selectedIconRes,
                title = item.value.titleRes,
                isSelected = item.key == selectedKey,
                onClick = { onSelectKey(item.key) }
            )
        }
    }
}

package com.devbilal.designsystem.component.bottomNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.home
import zat.designsystem.generated.resources.ic_home
import zat.designsystem.generated.resources.ic_home_selected

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable BottomNavigationScope.() -> Unit = {},
) {
    val scope = remember { BottomNavigationScopeImpl() }

    var version by remember { mutableIntStateOf(0) }

    scope.clear()
    content(scope)

    LaunchedEffect(scope.items) {
        snapshotFlow { scope.items.map { it.isSelected } }
            .collect {
                version++
            }
    }

    val displayItems by remember(version, scope.items) {
        derivedStateOf {
            scope.items.toList()
        }
    }

    BottomNavigationBarContent(
        items = displayItems,
        modifier = modifier.background(Theme.colorScheme.background.surfaceLow)
    )
}

@Preview
@Composable
private fun PreviewBottomNavigationBar() {
    ZatTheme {
        Box(Modifier.fillMaxSize()) {
            BottomNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                bottomNavigationItem(
                    selectedIcon = Res.drawable.ic_home_selected,
                    notSelectedIcon = Res.drawable.ic_home,
                    title = Res.string.home,
                    isSelected = true,
                    onClick = { }
                )

            }
        }
    }
}
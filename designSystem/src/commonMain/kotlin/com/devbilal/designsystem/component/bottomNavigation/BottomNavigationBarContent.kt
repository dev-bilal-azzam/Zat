package com.devbilal.designsystem.component.bottomNavigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.home
import zat.designsystem.generated.resources.ic_home
import zat.designsystem.generated.resources.ic_home_selected

@Composable
fun BottomNavigationBarContent(
    items: List<BottomNavigationItem>,
    modifier: Modifier = Modifier
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    BoxWithConstraints(modifier.height(74.dp)) {
        val itemWidth = maxWidth / items.size
        val indicatorWidth = itemWidth - 40.dp
        val indicatorOffset by animateDpAsState(
            targetValue = selectedItemIndex * itemWidth
        )

        Row(
            Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                if (item.isSelected) selectedItemIndex = index

                BottomNavigationBarItem(
                    isSelected = item.isSelected,
                    selectedIcon = vectorResource(item.selectedIcon),
                    unselectedIcon = vectorResource(item.notSelectedIcon),
                    title = stringResource(item.title),
                    onClick = { item.onClick() },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(
            Modifier
                .padding(horizontal = 20.dp)
                .offset(x = indicatorOffset)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = Theme.radius.xs,
                        bottomStart = Theme.radius.xs
                    )
                )
                .background(Theme.colorScheme.brand.brand)
                .size(indicatorWidth, 4.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewBottomNavigationBar() {
    ZatTheme {
        val items = listOf(
            BottomNavigationItem(
                selectedIcon = Res.drawable.ic_home_selected,
                notSelectedIcon = Res.drawable.ic_home,
                title = Res.string.home,
                isSelected = true,
                onClick = {}
            )
        )

        BottomNavigationBarContent(
            items = items,
            modifier = Modifier.background(Color.White)
        )
    }
}
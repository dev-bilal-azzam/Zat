package com.devbilal.designsystem.component.carousel

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun Carousel(
    state: PagerState,
    modifier: Modifier = Modifier,
    indicator: @Composable (BoxScope.() -> Unit)? = null,
    itemContent: @Composable (Int) -> Unit
) {
    Box(modifier = modifier) {
        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize(),
            beyondViewportPageCount = 1
        ) { page ->
            itemContent(page)
        }

        indicator?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Theme.spacing._24)
            ) {
                it()
            }
        }
    }
}

enum class IndicatorStyle {
    Pill, Expanding
}

@Composable
fun CarouselIndicator(
    itemCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    style: IndicatorStyle = IndicatorStyle.Expanding
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(itemCount) { index ->
            val isSelected = index == currentPage
            val width = if (style == IndicatorStyle.Expanding) {
                animateDpAsState(if (isSelected) 24.dp else 8.dp).value
            } else {
                8.dp
            }

            Box(
                modifier = Modifier
                    .size(width = width, height = 8.dp)
                    .background(
                        color = if (isSelected) Theme.colorScheme.primary.primary else Theme.colorScheme.disabled,
                        shape = RoundedCornerShape(Theme.radius.full)
                    )
            )
        }
    }
}

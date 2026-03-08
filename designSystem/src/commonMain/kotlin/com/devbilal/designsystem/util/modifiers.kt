package com.devbilal.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun Modifier.applyIf(
    condition: Boolean,
    newModifiers: @Composable Modifier.() -> Modifier
): Modifier = if (condition) this.newModifiers() else this

fun Modifier.autoMirror(): Modifier = composed {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    graphicsLayer { scaleX = if (isRtl) -1f else 1f }
}
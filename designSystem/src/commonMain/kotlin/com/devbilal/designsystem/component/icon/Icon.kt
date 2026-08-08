package com.devbilal.designsystem.component.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.devbilal.designsystem.util.applyIf

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    autoMirror: Boolean = true
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Icon(
        imageVector = imageVector,
        tint = tint,
        contentDescription = contentDescription,
        modifier = modifier
            .applyIf(autoMirror) {
                Modifier.graphicsLayer {
                    scaleX = if (isRtl) -1f else 1f
                }
            }

    )
}

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    autoMirror: Boolean = true
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Icon(
        painter = painter,
        tint = tint,
        contentDescription = contentDescription,
        modifier = modifier
            .applyIf(autoMirror) {
                Modifier.graphicsLayer {
                    scaleX = if (isRtl) -1f else 1f
                }
            }
    )
}

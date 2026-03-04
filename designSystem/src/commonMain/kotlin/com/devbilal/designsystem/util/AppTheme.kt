package com.devbilal.designsystem.util

import androidx.compose.ui.graphics.Color
import com.devbilal.designsystem.theme.color.colorPalette
import org.jetbrains.compose.resources.DrawableResource
import zat.designsystem.generated.resources.*

enum class AppTheme(
    val iconRes: DrawableResource,
    val tint: Color,
    val iconBackgroundColor: Color
) {
    DARK(
        iconRes = Res.drawable.ic_dark_theme,
        tint = colorPalette.gray.shade100,
        iconBackgroundColor = colorPalette.navy.shade900
    ),
    LIGHT(
        iconRes = Res.drawable.ic_light_theme,
        tint = colorPalette.gray.shade600,
        iconBackgroundColor = colorPalette.gray.shade100
    ),
    SYSTEM(
        iconRes = Res.drawable.ic_system_theme,
        tint = colorPalette.gray.shade400,
        iconBackgroundColor = colorPalette.gray.shade600
    )
}
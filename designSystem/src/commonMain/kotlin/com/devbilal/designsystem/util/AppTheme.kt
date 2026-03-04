package com.devbilal.designsystem.util

import androidx.compose.ui.graphics.Color
import com.devbilal.designsystem.theme.color.colorPalette
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.auto
import zat.designsystem.generated.resources.dark
import zat.designsystem.generated.resources.ic_dark_theme
import zat.designsystem.generated.resources.ic_light_theme
import zat.designsystem.generated.resources.ic_system_theme
import zat.designsystem.generated.resources.light

enum class AppTheme(
    val nameRes: StringResource,
    val iconRes: DrawableResource,
    val tint: Color,
    val iconBackgroundColor: Color
) {
    DARK(
        nameRes = Res.string.dark,
        iconRes = Res.drawable.ic_dark_theme,
        tint = colorPalette.gray.shade100,
        iconBackgroundColor = colorPalette.navy.shade900
    ),
    LIGHT(
        nameRes = Res.string.light,
        iconRes = Res.drawable.ic_light_theme,
        tint = colorPalette.gray.shade600,
        iconBackgroundColor = colorPalette.gray.shade100
    ),
    SYSTEM(
        nameRes = Res.string.auto,
        iconRes = Res.drawable.ic_system_theme,
        tint = colorPalette.gray.shade400,
        iconBackgroundColor = colorPalette.gray.shade600
    )
}
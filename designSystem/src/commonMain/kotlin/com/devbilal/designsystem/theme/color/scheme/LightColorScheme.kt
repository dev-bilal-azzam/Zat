package com.devbilal.designsystem.theme.color.scheme

import com.devbilal.designsystem.theme.color.White
import com.devbilal.designsystem.theme.color.White38
import com.devbilal.designsystem.theme.color.White60
import com.devbilal.designsystem.theme.color.colorPalette

internal val LightColorScheme = ColorScheme(
    brand = ColorScheme.Brand(
        brand = colorPalette.navy.shade500,
        brandVariant = colorPalette.navy.shade50,
        onBrand = White
    ),
    primary = ColorScheme.Primary(
        primary = colorPalette.navy.shade500,
        onPrimary = White,
        onPrimaryBody = White60,
        onPrimaryHint = White38
    ),
    secondary = ColorScheme.Secondary(
        secondary = colorPalette.coffee.shade800,
        secondaryText = colorPalette.coffee.shade600,
        secondaryVariant = colorPalette.coffee.shade200
    ),
    border = ColorScheme.Border(
        disabled = colorPalette.gray.shade500,
        brand = colorPalette.navy.shade900,
        error = colorPalette.red.shade700,
        success = colorPalette.green.shade700
    ),
    background = ColorScheme.Background(
        surfaceLow = colorPalette.navy.shade50,
        surface = colorPalette.navy.shade200,
        surfaceHigh = colorPalette.navy.shade300,
        bgError = colorPalette.red.shade50,
        bgWarning = colorPalette.yellow.shade50,
        bgSuccess = colorPalette.green.shade50
    ),
    shadePrimary = colorPalette.gray.shade800,
    shadeSecondary = colorPalette.gray.shade700,
    shadeTertiary = colorPalette.gray.shade600,
    stroke = colorPalette.gray.shade300,
    textDisabled = colorPalette.gray.shade500,
    disabled = colorPalette.gray.shade400,
    error = colorPalette.red.shade500,
    warning = colorPalette.yellow.shade600,
    success = colorPalette.green.shade600
)
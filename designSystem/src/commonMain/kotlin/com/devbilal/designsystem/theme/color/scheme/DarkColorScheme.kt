package com.devbilal.designsystem.theme.color.scheme

import com.devbilal.designsystem.theme.color.White
import com.devbilal.designsystem.theme.color.colorPalette

internal val DarkColorScheme = ColorScheme(
    brand = ColorScheme.Brand(
        brand = colorPalette.navy.shade500,
        brandVariant = colorPalette.navy.shade900,
        onBrand = White
    ),
    primary = ColorScheme.Primary(
        primary = colorPalette.navy.shade500,
        onPrimary = White,
        onPrimaryBody = colorPalette.gray.shade100,
        onPrimaryHint = colorPalette.gray.shade400
    ),
    secondary = ColorScheme.Secondary(
        secondary = colorPalette.coffee.shade800,
        secondaryText = colorPalette.coffee.shade600,
        secondaryVariant = colorPalette.coffee.shade200
    ),
    border = ColorScheme.Border(
        disabled = colorPalette.navy.shade800,
        brand = colorPalette.gray.shade800,
        error = colorPalette.red.shade500,
        success = colorPalette.green.shade500
    ),
    background = ColorScheme.Background(
        surfaceLow = colorPalette.navy.shade900,
        surface = colorPalette.navy.shade800,
        surfaceHigh = colorPalette.navy.shade700,
        bgError = colorPalette.red.shade900,
        bgWarning = colorPalette.yellow.shade800,
        bgSuccess = colorPalette.green.shade800
    ),
    shadePrimary = colorPalette.gray.shade100,
    shadeSecondary = colorPalette.gray.shade400,
    shadeTertiary = colorPalette.gray.shade500,
    stroke = colorPalette.gray.shade700,
    textDisabled = colorPalette.gray.shade500,
    disabled = colorPalette.gray.shade600,
    error = colorPalette.red.shade400,
    warning = colorPalette.yellow.shade300,
    success = colorPalette.green.shade300
)
package com.devbilal.designsystem.util

import org.jetbrains.compose.resources.StringResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.arabic_language
import zat.designsystem.generated.resources.default_language

enum class AppLanguage(
    val iso: String,
    val hintResource: StringResource
) {
    Arabic(
        iso = "ar",
        hintResource = Res.string.arabic_language
    ),
    English(
        iso = "en",
        hintResource = Res.string.default_language
    )
}
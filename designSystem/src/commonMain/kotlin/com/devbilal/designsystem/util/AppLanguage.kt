package com.devbilal.designsystem.util

import org.jetbrains.compose.resources.StringResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.arabic
import zat.designsystem.generated.resources.arabic_language
import zat.designsystem.generated.resources.default_language
import zat.designsystem.generated.resources.english

enum class AppLanguage(
    val nameRes: StringResource,
    val iso: String,
    val hintRes: StringResource
) {
    Arabic(
        nameRes = Res.string.arabic,
        iso = "ar",
        hintRes = Res.string.arabic_language
    ),
    English(
        nameRes = Res.string.english,
        iso = "en",
        hintRes = Res.string.default_language
    );

    companion object {
        fun fromIso(iso: String): AppLanguage {
            return when (iso.lowercase()) {
                English.iso -> English
                Arabic.iso -> Arabic
                else -> English
            }
        }
    }
}
package com.devbilal.designsystem.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devbilal.designsystem.theme.theme.Theme
import zat.designsystem.generated.resources.*
import com.devbilal.designsystem.util.AppLanguage
import org.jetbrains.compose.resources.Font

@Composable
fun createThemeTypography(appLanguage: String): Typography {
    val interFontFamily = FontFamily(
        Font(resource = Res.font.inter_regular, FontWeight.Normal),
        Font(resource = Res.font.inter_medium, FontWeight.Medium),
        Font(resource = Res.font.inter_semi_bold, FontWeight.SemiBold),
    )
    val cairoFontFamily = FontFamily(
        Font(resource = Res.font.cairo_regular, FontWeight.Normal),
        Font(resource = Res.font.cairo_medium, FontWeight.Medium),
        Font(resource = Res.font.cairo_semi_bold, FontWeight.SemiBold),
    )
    val fontFamily = when (appLanguage) {
        AppLanguage.English.iso -> interFontFamily
        AppLanguage.Arabic.iso -> cairoFontFamily
        else -> interFontFamily
    }

    return Typography(
        appName = TextStyle.Default.copy(
            fontSize = 28.sp,
            fontFamily = fontFamily,
            color = Theme.colorScheme.brand.brand
        ),
        headline = Typography.Headline(
            large = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 42.sp
            ),
            medium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                lineHeight = 36.sp
            ),
            small = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        ),
        title = Typography.Title(
            large = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 30.sp
            ),
            medium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 28.sp
            ),
            small = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        ),
        body = Typography.Body(
            large = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                lineHeight = 28.sp
            ),
            medium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            small = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        ),
        label = Typography.Label(
            large = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            medium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 22.sp
            ),
            small = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                lineHeight = 16.sp
            ),
            extraSmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
        )
    )
}
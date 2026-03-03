package com.devbilal.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.ic_cheese_cake
import com.devbilal.designsystem.component.button.content.BaseButtonContent
import com.devbilal.designsystem.component.preview.PreviewComponent
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import sv.lib.squircleshape.SquircleShape

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: Painter? = null,
    iconSize: Dp = 20.dp,
    contentDescription: String? = null,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    contentColor: Color = Theme.colorScheme.primary.primary,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = 13.dp
    ),
    shape: Shape = SquircleShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        shape = shape,
        borderStroke = BorderStroke(width = 1.dp, color = Theme.colorScheme.stroke),
        contentColor = contentColor,
        containerColor = Color.Transparent,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = Color.Transparent,
        contentPadding = contentPadding,
        onClick = onClick,
        isLoading = isLoading,
        loadingColors = listOf(
            Theme.colorScheme.stroke,
            Theme.colorScheme.shadeTertiary,
            Theme.colorScheme.primary.primary
        ),
        modifier = modifier
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = trailingIcon,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            contentDescription = contentDescription,
            contentColor = it
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OutlinedButtonPreview() {
    ZatTheme {
        PreviewComponent(
            isScrollable = true,
            title = "Outlined button"
        ) {
            OutlinedButton(
                text = "Button",
                trailingIcon = painterResource(resource = Res.drawable.ic_cheese_cake),
                onClick = {},
                modifier = Modifier
            )
            OutlinedButton(
                text = "Button",
                isLoading = true,
                trailingIcon = painterResource(resource = Res.drawable.ic_cheese_cake),
                onClick = {},
                modifier = Modifier
            )
            OutlinedButton(
                text = "Button",
                trailingIcon = painterResource(resource = Res.drawable.ic_cheese_cake),
                onClick = {},
                isEnabled = false,
                modifier = Modifier
            )
        }
    }
}

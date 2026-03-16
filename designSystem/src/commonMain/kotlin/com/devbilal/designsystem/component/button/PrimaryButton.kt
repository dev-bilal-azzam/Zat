package com.devbilal.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.content.BaseButtonContent
import com.devbilal.designsystem.component.preview.PreviewComponent
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import sv.lib.squircleshape.SquircleShape
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.ic_cheese_cake

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    iconSize: Dp = 16.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.primary.primary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = 12.dp
    ),
    shape: Shape = SquircleShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        shape = shape,
        isLoading = isLoading,
        loadingColors = listOf(
            Theme.colorScheme.primary.onPrimaryHint,
            Theme.colorScheme.primary.onPrimaryBody,
            Theme.colorScheme.primary.onPrimary
        ),
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            text = text,
            contentColor = it,
            trailingIcon = trailingIcon,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    ZatTheme {
        var primaryButtonLoading by remember { mutableStateOf(false) }

        LaunchedEffect(primaryButtonLoading) {
            if (primaryButtonLoading) {
                launch {
                    delay(1000)
                    primaryButtonLoading = false
                }
            }
        }

        PreviewComponent(
            isScrollable = true,
            title = "Primary button"
        ) {
            PrimaryButton(
                text = "Button",
                trailingIcon = vectorResource(resource = Res.drawable.ic_cheese_cake),
                onClick = {},
                modifier = Modifier
            )
            PrimaryButton(
                text = "Click me to test loading",
                isLoading = primaryButtonLoading,
                trailingIcon = vectorResource(resource = Res.drawable.ic_cheese_cake),
                onClick = { primaryButtonLoading = !primaryButtonLoading },
            )
            PrimaryButton(
                text = "Button",
                trailingIcon = vectorResource(resource = Res.drawable.ic_cheese_cake),
                onClick = {},
                isEnabled = false,
                modifier = Modifier
            )
        }
    }
}

package com.devbilal.designsystem.component.richtext

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.devbilal.designsystem.theme.color.colorPalette
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.ic_selected
import com.devbilal.richtext.RichTextPanel as BaseRichTextPanel

@Composable
fun RichTextPanel(
    state: RichTextState,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        colorPalette.navy.shade500,
        colorPalette.gray.shade50,
        colorPalette.gray.shade900,
        colorPalette.red.shade400,
        colorPalette.yellow.shade600,
        colorPalette.green.shade600,
        colorPalette.coffee.shade900,
        colorPalette.violet.shade900
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.lg),
    backgroundColor: Color = Theme.colorScheme.background.surface,
    contentColor: Color = Theme.colorScheme.shadePrimary,
    selectedContentColor: Color = Theme.colorScheme.primary.primary,
    selectedContainerColor: Color = Theme.colorScheme.primary.primary.copy(alpha = 0.1f),
    selectedColorBorderColor: Color = Theme.colorScheme.primary.primary,
    chooseColorTitle: String = "TEXT COLOR",
    chooseColorTitleStyle: TextStyle = Theme.typography.label.extraSmall,
    chooseColorTitleColor: Color = Theme.colorScheme.shadeTertiary,
    itemTextStyle: TextStyle = Theme.typography.title.medium,
    selectionIconRes: DrawableResource = Res.drawable.ic_selected,
) {
    BaseRichTextPanel(
        state = state.delegate,
        colors = colors,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        selectedContentColor = selectedContentColor,
        selectedContainerColor = selectedContainerColor,
        selectedColorBorderColor = selectedColorBorderColor,
        chooseColorTitle = chooseColorTitle,
        chooseColorTitleStyle = chooseColorTitleStyle,
        chooseColorTitleColor = chooseColorTitleColor,
        itemTextStyle = itemTextStyle,
        selectionIconRes = selectionIconRes,
        modifier = modifier
    )
}

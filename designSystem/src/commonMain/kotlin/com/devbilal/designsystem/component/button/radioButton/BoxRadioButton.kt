package com.devbilal.designsystem.component.button.radioButton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.ic_selected
import zat.designsystem.generated.resources.selected


@Composable
fun BoxRadioButton(
    isSelected: Boolean,
    onClick: (() -> Unit)?,
    label: String? = null,
    icon: Painter? = null,
    selectedIndicatorIcon: Painter = painterResource(Res.drawable.ic_selected),
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.full),
    isEnabled: Boolean = true
) {

    val animatedBorderDp by animateDpAsState(
        targetValue = if (isSelected) 6.dp else 1.dp
    )

    val animatedSelectionBorderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.primary.primary else Theme.colorScheme.stroke
    )

    val animatedDisabledBorderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.disabled else Theme.colorScheme.border.disabled
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isEnabled) animatedSelectionBorderColor else animatedDisabledBorderColor
    )

    val animatedUnselectedContentColor by animateColorAsState(
        targetValue = if (isSelected || !isEnabled) Color.Unspecified else Theme.colorScheme.background.surfaceLow
    )

    val animatedUnselectedLabelColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.colorScheme.shadePrimary else Theme.colorScheme.shadeTertiary
    )

    val animatedLabelColor by animateColorAsState(
        targetValue = if (isEnabled)
            animatedUnselectedLabelColor else Theme.colorScheme.shadePrimary
    )

    val clickableModifier = onClick?.let {
        Modifier.clickable(
            enabled = isEnabled,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            role = Role.RadioButton
        ) {
            onClick()
        }
    } ?: Modifier

    Box(
        modifier = modifier,
    ) {
        if (isSelected) {
            Icon(
                painter = selectedIndicatorIcon,
                contentDescription = stringResource(Res.string.selected),
                tint = Theme.colorScheme.brand.brand,
                modifier = Modifier.size(12.dp).align(Alignment.TopEnd)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {


            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(animatedUnselectedContentColor, shape)
                    .border(
                        width = animatedBorderDp,
                        color = animatedBorderColor,
                        shape = shape
                    )
                    .clip(shape)
                    .then(clickableModifier),
            ) {
                icon?.let {
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            label?.let { text ->
                Text(
                    text = text,
                    color = animatedLabelColor,
                    style = Theme.typography.headline.small
                )
            }

        }
    }

}

@Preview
@Composable
private fun RadioButtonPreview() {
    ZatTheme {
        var selected by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Theme.colorScheme.background.surface),
            contentAlignment = Alignment.Center
        ) {
            RadioButton(
                isSelected = selected,
                label = "Label",
                isEnabled = false,
                onClick = {
                    selected = !selected
                }
            )
        }
    }
}
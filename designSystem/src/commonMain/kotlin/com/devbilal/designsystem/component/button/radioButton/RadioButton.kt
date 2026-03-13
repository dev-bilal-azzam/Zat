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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme


@Composable
fun RadioButton(
    isSelected: Boolean,
    onClick: (() -> Unit)?,
    label: String? = null,
    hint: String? = null,
    backgroundColor: Color? = null,
    borderColor: Color = Theme.colorScheme.border.disabled,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.xl),
    labelColor: Color = Theme.colorScheme.shadePrimary,
    hintColor: Color = Theme.colorScheme.shadeTertiary,
    isEnabled: Boolean = true,
    icon: ImageVector? = null,
    iconTint: Color = Theme.colorScheme.shadeTertiary,
    boxedIconTint: Color = Theme.colorScheme.primary.primary,
    showIconBackground: Boolean = false,
) {

    val animatedContainerColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.primary.primary.copy(alpha = .2f) else Color.Transparent
    )

    val animatedBorderDp by animateDpAsState(
        targetValue = if (isSelected) 6.dp else 1.dp
    )

    val animatedSelectionBorderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.primary.primary else Theme.colorScheme.border.disabled
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

    Row(
        modifier = modifier
            .background(backgroundColor ?: animatedContainerColor, shape)
            .border(2.dp, borderColor, shape)
            .padding(16.dp)
            .then(clickableModifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start)
    ) {
        if (showIconBackground) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.primary.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = boxedIconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
        }


        Column {
            label?.let { text ->
                Text(
                    text = text,
                    color = labelColor,
                    style = Theme.typography.headline.small,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            hint?.let { text ->
                Text(
                    text = text,
                    color = hintColor,
                    style = Theme.typography.label.extraSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(18.dp)
                .background(animatedUnselectedContentColor, shape)
                .border(
                    width = animatedBorderDp,
                    color = animatedBorderColor,
                    shape = shape
                )
                .clip(shape),
        )
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    ZatTheme(
        appTheme = "DARK"
    ) {
        var selected by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Theme.colorScheme.background.surfaceLow),
            contentAlignment = Alignment.Center
        ) {
            RadioButton(
                isSelected = selected,
                label = "English",
                hint = "App Default Language",
                isEnabled = true,
                onClick = {
                    selected = !selected
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
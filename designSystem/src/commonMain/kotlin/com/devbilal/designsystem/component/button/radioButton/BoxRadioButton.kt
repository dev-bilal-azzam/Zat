package com.devbilal.designsystem.component.button.radioButton

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppTheme
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
    iconTint: Color = Theme.colorScheme.brand.onBrand,
    iconBackgroundColor: Color = Color.Unspecified,
    selectedIndicatorIcon: Painter = painterResource(Res.drawable.ic_selected),
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.xl),
    borderColor: Color = Theme.colorScheme.border.disabled,
    labelColor: Color = Theme.colorScheme.shadePrimary,
    isEnabled: Boolean = true
) {

    val animatedContainerColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.primary.primary.copy(alpha = .2f) else Color.Transparent
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
        modifier = modifier
            .background(animatedContainerColor, shape)
            .border(2.dp, borderColor, shape)
            .padding(12.dp)
            .then(clickableModifier),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                painter = selectedIndicatorIcon,
                contentDescription = stringResource(Res.string.selected),
                tint = Theme.colorScheme.brand.brand,
                modifier = Modifier.size(16.dp).align(Alignment.TopEnd)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {


            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconBackgroundColor, shape)
                    .clip(shape),
                contentAlignment = Alignment.Center
            ) {
                icon?.let {
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            label?.let { text ->
                Text(
                    text = text,
                    color = labelColor,
                    style = Theme.typography.label.medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }

}

@Preview
@Composable
private fun RadioButtonPreview() {
    ZatTheme(
        appTheme = AppTheme.DARK.name
    ) {
        var selected by remember { mutableStateOf(true) }

        Surface(
            color = Theme.colorScheme.background.surfaceLow
        ) {
            BoxRadioButton(
                isSelected = selected,
                label = "Label",
                icon = painterResource(Res.drawable.ic_selected),
                iconBackgroundColor = Theme.colorScheme.brand.brand,
                onClick = {
                    selected = !selected
                }
            )
        }

    }
}
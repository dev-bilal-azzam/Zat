package com.devbilal.designsystem.component.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun ZatSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        onValueChangeFinished = onValueChangeFinished,
        interactionSource = interactionSource,
        valueRange = valueRange,
        colors = SliderDefaults.colors(
            thumbColor = Theme.colorScheme.primary.primary,
            activeTrackColor = Theme.colorScheme.primary.primary,
            inactiveTrackColor = Theme.colorScheme.disabled
        )
    )
}

package com.devbilal.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun ZatIconButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Theme.colorScheme.background.surface,
    contentColor: Color = Theme.colorScheme.primary.primary
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (enabled) containerColor else Theme.colorScheme.disabled)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = if (enabled) contentColor else Theme.colorScheme.textDisabled,
            modifier = Modifier.size(24.dp)
        )
    }
}

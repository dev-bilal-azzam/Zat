package com.devbilal.presentation.features.unlock.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_fingerprint

@Composable
fun BiometricButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.ic_fingerprint),
            contentDescription = "Biometric Unlock",
            tint = Theme.colorScheme.primary.primary,
            modifier = Modifier.size(32.dp)
        )
    }
}

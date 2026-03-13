package com.devbilal.presentation.features.auth.screens.initbiometric.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_fingerprint


@Composable
fun BiometricIllustration() {
    Box(
        modifier = Modifier
            .size(180.dp)
            .background(
                color = Theme.colorScheme.primary.primary.copy(alpha = 0.05f),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = Theme.colorScheme.primary.primary.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.ic_fingerprint),
            contentDescription = null,
            tint = Theme.colorScheme.primary.primary,
            modifier = Modifier.size(80.dp)
        )
    }
}


@Composable
@Preview
fun PreviewBiometricIllustration() {
    ZatTheme(appTheme = AppTheme.DARK.name) {
        Box(modifier = Modifier.background(Theme.colorScheme.background.surfaceLow).padding(16.dp)) {
            BiometricIllustration()
        }
    }
}

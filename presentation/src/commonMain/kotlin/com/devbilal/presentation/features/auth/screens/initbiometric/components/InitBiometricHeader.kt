package com.devbilal.presentation.features.auth.screens.initbiometric.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.biometric_setup_message
import zat.presentation.generated.resources.enable_biometric_unlock

@Composable
fun InitBiometricHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BiometricIllustration()

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(Res.string.enable_biometric_unlock),
            style = Theme.typography.headline.medium,
            color = Theme.colorScheme.shadePrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.biometric_setup_message),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun PreviewInitBiometricHeader() {
    ZatTheme{
        InitBiometricHeader()
    }
}
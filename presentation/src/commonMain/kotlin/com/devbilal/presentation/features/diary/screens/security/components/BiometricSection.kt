package com.devbilal.presentation.features.diary.screens.security.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.switch.Switch
import com.devbilal.presentation.features.diary.common.components.SettingsItem
import com.devbilal.presentation.features.diary.common.components.SettingsSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.biometric_authentication
import zat.presentation.generated.resources.ic_fingerprint
import zat.presentation.generated.resources.use_biometrics
import zat.presentation.generated.resources.use_biometrics_desc


@Composable
fun BiometricSection(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {


    SettingsSection(
        title = stringResource(Res.string.biometric_authentication)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            SettingsItem(
                title = stringResource(Res.string.use_biometrics),
                icon = vectorResource(Res.drawable.ic_fingerprint),
                description = stringResource(Res.string.use_biometrics_desc),
                showIconBackground = true
            )

            Switch(
                isChecked = isEnabled,
                onCheckedChange = onToggle,
                modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp)
            )
        }
    }
}

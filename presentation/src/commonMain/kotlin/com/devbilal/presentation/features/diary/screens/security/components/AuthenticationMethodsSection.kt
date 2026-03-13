package com.devbilal.presentation.features.diary.screens.security.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.features.diary.common.components.SettingsSection
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.authentication_methods
import zat.presentation.generated.resources.change_pattern
import zat.presentation.generated.resources.change_pin
import zat.presentation.generated.resources.ic_123
import zat.presentation.generated.resources.ic_pattern
import zat.presentation.generated.resources.pattern_lock
import zat.presentation.generated.resources.pattern_lock_desc
import zat.presentation.generated.resources.pin_lock
import zat.presentation.generated.resources.pin_lock_desc


@Composable
fun AuthenticationMethodsSection(
    selectedMethod: AuthenticationMethod?,
    onMethodSelected: (AuthenticationMethod) -> Unit,
    onChangePin: () -> Unit,
    onChangePattern: () -> Unit
) {
    SettingsSection(
        title = stringResource(Res.string.authentication_methods),
        backgroundColor = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AuthenticationMethodItem(
                label = stringResource(Res.string.pin_lock),
                hint = stringResource(Res.string.pin_lock_desc),
                iconRes = Res.drawable.ic_123,
                isSelected = selectedMethod is AuthenticationMethod.Pin,
                onSelect = { onMethodSelected(AuthenticationMethod.Pin(null)) },
                onAction = onChangePin,
                actionLabel = stringResource(Res.string.change_pin)
            )

            AuthenticationMethodItem(
                label = stringResource(Res.string.pattern_lock),
                hint = stringResource(Res.string.pattern_lock_desc),
                iconRes = Res.drawable.ic_pattern,
                isSelected = selectedMethod is AuthenticationMethod.Pattern,
                onSelect = { onMethodSelected(AuthenticationMethod.Pattern(null)) },
                onAction = onChangePattern,
                actionLabel = stringResource(Res.string.change_pattern)
            )
        }
    }
}

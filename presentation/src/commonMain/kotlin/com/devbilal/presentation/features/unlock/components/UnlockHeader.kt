package com.devbilal.presentation.features.unlock.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.*

@Composable
fun UnlockHeader(
    primaryMethod: PrimaryAuthenticationMethod,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Zat",
            style = Theme.typography.headline.large,
            color = Theme.colorScheme.primary.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = stringResource(Res.string.welcome_back),
            style = Theme.typography.headline.small,
            color = Theme.colorScheme.shadePrimary
        )

        val message = when (primaryMethod) {
            is PrimaryAuthenticationMethod.Pin -> Res.string.enter_pin_to_unlock
            is PrimaryAuthenticationMethod.Pattern -> Res.string.enter_pattern_to_unlock
            else -> Res.string.welcome_back // Should not happen
        }

        Text(
            text = stringResource(message),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )
    }
}

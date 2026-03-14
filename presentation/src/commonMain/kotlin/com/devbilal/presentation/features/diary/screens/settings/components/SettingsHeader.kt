package com.devbilal.presentation.features.diary.screens.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.zat
import zat.presentation.generated.resources.zat_logo

@Composable
fun SettingsHeader(
    modifier: Modifier = Modifier,
    version: String?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.zat_logo),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.zat),
            style = Theme.typography.headline.medium,
            color = Theme.colorScheme.shadePrimary
        )

        Text(
            text = version ?: "0.0.0",
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.shadeTertiary
        )
    }
}

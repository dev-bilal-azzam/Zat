package com.devbilal.presentation.features.auth.screens.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.welcome
import zat.presentation.generated.resources.welcome_message
import zat.presentation.generated.resources.zat
import zat.presentation.generated.resources.zat_logo

@Composable
fun OnBoardingHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.zat_logo),
            contentDescription = stringResource(Res.string.zat),
            modifier = Modifier.size(198.dp)
        )

        Text(
            text = stringResource(Res.string.welcome),
            style = Theme.typography.headline.large,
            color = Theme.colorScheme.shadePrimary
        )

        Text(
            text = stringResource(Res.string.welcome_message),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )
    }

}
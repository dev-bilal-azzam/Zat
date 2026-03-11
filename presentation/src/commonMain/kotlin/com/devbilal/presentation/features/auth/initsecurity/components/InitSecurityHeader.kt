package com.devbilal.presentation.features.auth.initsecurity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_lock
import zat.presentation.generated.resources.security_init_message
import zat.presentation.generated.resources.your_privacy_secured

@Composable
fun InitSecurityHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment= Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .background(
                    Theme.colorScheme.brand.brand.copy(alpha = .2f),
                    RoundedCornerShape(Theme.radius.full)
                )
                .size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_lock),
                contentDescription = null,
                tint = Theme.colorScheme.brand.brand,
                modifier = Modifier.height(42.dp).width(32.dp)
            )
        }

        Text(
            text = stringResource(Res.string.your_privacy_secured),
            style = Theme.typography.headline.large,
            color = Theme.colorScheme.shadePrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(Res.string.security_init_message),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
@Preview
fun PreviewInitSecurityHeader() {
    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }
    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        Column(
            modifier = Modifier.background(Theme.colorScheme.background.surfaceLow),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InitSecurityHeader()
        }
    }
}
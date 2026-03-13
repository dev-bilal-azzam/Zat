package com.devbilal.presentation.features.diary.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.divider.HorizontalDivider
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.contact_support
import zat.presentation.generated.resources.ic_info
import zat.presentation.generated.resources.ic_mail
import zat.presentation.generated.resources.privacy_policy

@Composable
fun SettingsFooter(
    modifier: Modifier = Modifier,
    onPrivacyPolicyClicked: () -> Unit = {},
    onContactSupportClicked: () -> Unit = {}
) {
    SettingsSection(modifier = modifier) {
        Column {
            SettingsItem(
                icon = vectorResource(Res.drawable.ic_info),
                title = stringResource(Res.string.privacy_policy),
                onClick = { onPrivacyPolicyClicked() },
                showIconBackground = false
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Theme.colorScheme.border.disabled
            )
            SettingsItem(
                icon = vectorResource(Res.drawable.ic_mail),
                title = stringResource(Res.string.contact_support),
                onClick = { onContactSupportClicked() },
                showIconBackground = false
            )
        }
    }
}


@Composable
@Preview
fun SettingsFooterPreview() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        SettingsFooter()

    }
}
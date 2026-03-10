package com.devbilal.presentation.features.initsecurity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.common.components.ZatHorizontalItemCard
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.biometrics
import zat.presentation.generated.resources.biometrics_label
import zat.presentation.generated.resources.ic_123
import zat.presentation.generated.resources.ic_arrow_right_ios
import zat.presentation.generated.resources.ic_fingerprint
import zat.presentation.generated.resources.ic_lock
import zat.presentation.generated.resources.ic_pattern
import zat.presentation.generated.resources.pattern
import zat.presentation.generated.resources.pattern_label
import zat.presentation.generated.resources.pin
import zat.presentation.generated.resources.pin_label
import zat.presentation.generated.resources.unlock_methods

@Composable
fun UnlockMethodsSection(
    modifier: Modifier = Modifier,
    onPinClicked: (() -> Unit)? = null,
    onPatternClicked: (() -> Unit)? = null
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.unlock_methods),
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.brand.brand,
            modifier = Modifier.align(Alignment.Start)
        )

        ZatHorizontalItemCard(
            label = stringResource(Res.string.pin),
            hint = stringResource(Res.string.pin_label),
            leadingIconRes = Res.drawable.ic_123,
            trailingIconRes = Res.drawable.ic_arrow_right_ios,
            modifier = Modifier.fillMaxWidth(),
            onClick = onPinClicked
        )

        ZatHorizontalItemCard(
            label = stringResource(Res.string.pattern),
            hint = stringResource(Res.string.pattern_label),
            leadingIconRes = Res.drawable.ic_pattern,
            trailingIconRes = Res.drawable.ic_arrow_right_ios,
            modifier = Modifier.fillMaxWidth(),
            onClick = onPatternClicked
        )

        ZatHorizontalItemCard(
            label = stringResource(Res.string.biometrics),
            hint = stringResource(Res.string.biometrics_label),
            leadingIconRes = Res.drawable.ic_fingerprint,
            trailingIconRes = Res.drawable.ic_lock,
            modifier = Modifier.fillMaxWidth(),
            isEnabled = false
        )

    }

}

@Composable
@Preview
fun PreviewUnlockMethodsSection() {
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
            UnlockMethodsSection()
        }
    }
}
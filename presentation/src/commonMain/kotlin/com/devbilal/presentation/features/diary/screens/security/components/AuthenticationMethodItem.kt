package com.devbilal.presentation.features.diary.screens.security.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.radioButton.RadioButton
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.change_pin
import zat.presentation.generated.resources.ic_123
import zat.presentation.generated.resources.ic_arrow_right_ios
import zat.presentation.generated.resources.pin_lock
import zat.presentation.generated.resources.pin_lock_desc


@Composable
fun AuthenticationMethodItem(
    label: String,
    hint: String,
    iconRes: DrawableResource,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onAction: () -> Unit,
    actionLabel: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.xl))
            .background(Theme.colorScheme.background.surface.copy(alpha = .5f))
            .clickable { if (!isSelected) onSelect() }
    ) {
        RadioButton(
            icon = vectorResource(iconRes),
            label = label,
            hint = hint,
            isSelected = isSelected,
            onClick = onSelect,
            borderColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth()
        )

        if (isSelected) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction() }
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = actionLabel,
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.primary.primary
                )
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_arrow_right_ios),
                    contentDescription = null,
                    tint = Theme.colorScheme.primary.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}



@Composable
@Preview
fun SecurityPreview() {
    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        AuthenticationMethodItem(
            label = stringResource(Res.string.pin_lock),
            hint = stringResource(Res.string.pin_lock_desc),
            iconRes = Res.drawable.ic_123,
            isSelected = true,
            onSelect = {  },
            onAction = { },
            actionLabel = stringResource(Res.string.change_pin)
        )
    }
}

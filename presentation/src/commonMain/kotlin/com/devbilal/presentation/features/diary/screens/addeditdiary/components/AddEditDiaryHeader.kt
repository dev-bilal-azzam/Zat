package com.devbilal.presentation.features.diary.screens.addeditdiary.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.common.utils.formatDisplay
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.date
import zat.presentation.generated.resources.ic_calendar

@Composable
fun AddEditDiaryHeader(
    date: LocalDate,
    onDateClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(Res.string.date),
                style = Theme.typography.label.extraSmall,
                color = Theme.colorScheme.shadeTertiary
            )
            ReadOnlyField(
                text = date.formatDisplay(),
                icon = Res.drawable.ic_calendar,
                onClick = onDateClicked
            )
        }
    }
}

@Composable
private fun ReadOnlyField(
    text: String,
    icon: org.jetbrains.compose.resources.DrawableResource,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surface)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = null,
            tint = Theme.colorScheme.primary.primary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadePrimary
        )
    }
}

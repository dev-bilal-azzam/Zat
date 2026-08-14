package com.devbilal.designsystem.component.datetime

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.TextButton
import com.devbilal.designsystem.theme.theme.Theme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.cancel
import zat.designsystem.generated.resources.ok
import kotlin.time.Instant

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Theme.colorScheme.background.surface
    ) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(
                    text = stringResource(Res.string.ok),
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(LocalDate.fromEpochMillis(millis))
                        }
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            },
            dismissButton = {
                TextButton(
                    text = stringResource(Res.string.cancel),
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = Theme.colorScheme.background.surface,
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    titleContentColor = Theme.colorScheme.primary.primary,
                    headlineContentColor = Theme.colorScheme.primary.primary,
                    navigationContentColor = Theme.colorScheme.primary.primary,
                    subheadContentColor = Theme.colorScheme.primary.primary,
                    weekdayContentColor = Theme.colorScheme.primary.primary,
                    dayContentColor = Theme.colorScheme.primary.primary,
                    disabledDayContentColor = Theme.colorScheme.primary.primary,
                    selectedDayContentColor = Theme.colorScheme.background.surface,
                    selectedDayContainerColor = Theme.colorScheme.primary.primary,
                    todayDateBorderColor = Theme.colorScheme.primary.primary,
                    todayContentColor = Theme.colorScheme.primary.primary,
                    yearContentColor = Theme.colorScheme.primary.primary,
                    selectedYearContentColor = Theme.colorScheme.background.surface,
                    selectedYearContainerColor = Theme.colorScheme.primary.primary,
                    currentYearContentColor = Theme.colorScheme.primary.primary,
                    containerColor = Theme.colorScheme.background.surface,
                )
            )
        }
    }
}

private fun LocalDate.Companion.fromEpochMillis(millis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(millis)
    return instant.toLocalDateTime(
        TimeZone.currentSystemDefault()
    ).date
}
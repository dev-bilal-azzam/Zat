package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.button.PrimaryButton
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.add_entry
import zat.presentation.generated.resources.edit_entry
import zat.presentation.generated.resources.save

@Composable
fun AddEditDiaryAppBar(
    isEditMode: Boolean = false,
    isSaveEnabled: Boolean = true,
    onBackClicked: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    AppBar(
        title = if (isEditMode) stringResource(Res.string.edit_entry)
        else stringResource(Res.string.add_entry),
        onLeadingClick = onBackClicked ,
        trailingContent = {
            PrimaryButton(
                text = stringResource(Res.string.save),
                onClick = onSaveClick,
                isEnabled = isSaveEnabled,
                contentPadding = PaddingValues(horizontal = 12.dp , vertical = 4.dp),

                modifier = Modifier.height(40.dp)
            )
        }
    )
}

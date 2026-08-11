package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.button.ZatIconButton
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.*

@Composable
fun AddEditDiaryAppBar(
    isEditMode: Boolean = false,
    isSaveEnabled: Boolean = true,
    onBackClicked: () -> Unit = {},
    onDeleteClicked: (() -> Unit)? = null,
    onSaveClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    AppBar(
        title = if (isEditMode) stringResource(Res.string.edit_entry)
        else stringResource(Res.string.add_entry),
        onLeadingClick = onBackClicked,
        trailingContent = {
            onDeleteClicked?.let {
                ZatIconButton(
                    painter = painterResource(Res.drawable.ic_delete),
                    onClick = it,
                    contentColor = Theme.colorScheme.error,
                    modifier = Modifier.size(40.dp)
                )
            }
            PrimaryButton(
                text = stringResource(Res.string.save),
                onClick = onSaveClick,
                isEnabled = isSaveEnabled,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(40.dp)
            )
        },
        modifier = modifier
    )
}

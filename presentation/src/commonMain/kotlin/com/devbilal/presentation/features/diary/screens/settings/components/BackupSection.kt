package com.devbilal.presentation.features.diary.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devbilal.presentation.features.diary.common.components.SettingsSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.backup_restore
import zat.presentation.generated.resources.export_data
import zat.presentation.generated.resources.ic_export_cloud
import zat.presentation.generated.resources.ic_import_cloud
import zat.presentation.generated.resources.import_data

@Composable
fun BackupSection(
    modifier: Modifier = Modifier,
    onExportClicked: () -> Unit,
    onImportClicked: () -> Unit
) {
    SettingsSection(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        title = stringResource(Res.string.backup_restore)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BackupCard(
                icon = vectorResource(Res.drawable.ic_export_cloud),
                title = stringResource(Res.string.export_data),
                onClick = { onExportClicked() },
                modifier = Modifier.weight(1f)
            )
            BackupCard(
                icon = vectorResource(Res.drawable.ic_import_cloud),
                title = stringResource(Res.string.import_data),
                onClick = { onImportClicked() },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
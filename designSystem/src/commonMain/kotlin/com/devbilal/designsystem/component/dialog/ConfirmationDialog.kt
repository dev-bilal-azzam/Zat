package com.devbilal.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.colorScheme.background.surface)
                .padding(Theme.spacing._24),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = Theme.typography.title.large,
                color = Theme.colorScheme.shadePrimary
            )
            Spacer(modifier = Modifier.height(Theme.spacing._16))
            Text(
                text = message,
                style = Theme.typography.body.medium,
                color = Theme.colorScheme.shadeSecondary
            )
            Spacer(modifier = Modifier.height(Theme.spacing._24))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing._12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryButton(
                    text = cancelText,
                    onClick = onCancel,
                    containerColor = Color.Transparent,
                    contentColor = Theme.colorScheme.primary.primary,
                    modifier = Modifier.weight(1f)
                )
                PrimaryButton(
                    text = confirmText,
                    onClick = onConfirm,
                    containerColor = Theme.colorScheme.error,
                    contentColor = Theme.colorScheme.background.surface,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

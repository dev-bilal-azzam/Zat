
package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.ZatBottomSheet
import com.devbilal.designsystem.component.button.TextButton
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.*



@Composable
fun AttachmentBottomSheet(
    title: String,
    onDismissRequest: () -> Unit,
    options: List<AttachmentOption>,
    modifier: Modifier = Modifier
) {
    ZatBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = title,
                style = Theme.typography.title.large,
                color = Theme.colorScheme.shadePrimary
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                options.forEach { option ->
                    AttachmentOptionItem(
                        option = option,
                        onClick = {
                            option.onClick()
                        }
                    )
                }
            }

            TextButton(
                text = stringResource(Res.string.cancel),
                onClick = onDismissRequest,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

data class AttachmentOption(
    val title: String,
    val description: String,
    val icon: DrawableResource,
    val onClick: () -> Unit
)

@Composable
private fun AttachmentOptionItem(
    option: AttachmentOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.colorScheme.background.surface)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Theme.colorScheme.primary.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(option.icon),
                tint = Theme.colorScheme.primary.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = option.title,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.shadePrimary
            )
            Text(
                text = option.description,
                style = Theme.typography.body.small,
                color = Theme.colorScheme.shadeTertiary
            )
        }

        Icon(
            painter = painterResource(Res.drawable.ic_arrow_right),
            tint = Theme.colorScheme.shadeTertiary,
            modifier = Modifier.size(20.dp)
        )
    }
}

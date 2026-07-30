@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.entity.Attachment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.*
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun AttachmentPreview(
    attachments: List<Attachment>,
    onRemoveAttachment: (Attachment) -> Unit,
    modifier: Modifier = Modifier
) {
    if (attachments.isEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(attachments) { attachment ->
                AttachmentPreviewItem(
                    attachment = attachment,
                    onRemove = { onRemoveAttachment(attachment) }
                )
            }
        }
    }
}

@Composable
private fun AttachmentPreviewItem(
    attachment: Attachment,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(Theme.radius.md))
                .background(Theme.colorScheme.background.surface)
        ) {
            when (attachment) {
                is Attachment.Image -> {
                    var bitmap by remember(attachment.id) { mutableStateOf<ImageBitmap?>(null) }
                    
                    LaunchedEffect(attachment.id) {
                        bitmap = withContext(Dispatchers.Default) {
                            attachment.bytes.decodeToImageBitmap()
                        }
                    }
                    
                    bitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: Image(
                        painter = painterResource(Res.drawable.ic_add_image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                is Attachment.Video -> {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Theme.colorScheme.primary.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_video),
                            tint = Color.White
                        )
                    }
                }
                is Attachment.Audio -> {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Theme.colorScheme.primary.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_mic),
                            tint = Theme.colorScheme.primary.primary
                        )
                    }
                }
            }
        }

        // Remove button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(Theme.colorScheme.error.copy(alpha = 0.8f))
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "×",
                color = Color.White,
                style = Theme.typography.label.small
            )
        }
    }
}

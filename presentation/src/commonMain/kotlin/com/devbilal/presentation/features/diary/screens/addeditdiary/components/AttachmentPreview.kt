@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.entity.Attachment
import org.jetbrains.compose.resources.painterResource
import sv.lib.squircleshape.SquircleShape
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_mic
import zat.presentation.generated.resources.ic_play
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun AttachmentPreview(
    attachments: List<Attachment>,
    onRemoveAttachment: (Attachment) -> Unit,
    onClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (attachments.isEmpty()) return

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(
            items = attachments,
            key = { _, item -> item.id.toString() }
        ) { index, attachment ->
            AttachmentItem(
                attachment = attachment,
                onRemove = { onRemoveAttachment(attachment) },
                modifier = Modifier.clickable { onClick(index) }
            )
        }
    }
}

@Composable
private fun AttachmentItem(
    attachment: Attachment,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(90.dp)
            .clip(SquircleShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surface)
    ) {
        when (attachment) {
            is Attachment.Image -> {
                AsyncImage(
                    model = attachment.filePath,
                    contentDescription = "Image Attachment",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is Attachment.Video -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = attachment.thumbnail,
                        contentDescription = "Video Thumbnail",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_play),
                            contentDescription = "Video",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )

                    }
                }
            }

            is Attachment.Audio -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_mic),
                        contentDescription = "Audio Attachment",
                        tint = Theme.colorScheme.primary.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Audio",
                        style = Theme.typography.label.small,
                        color = Theme.colorScheme.shadePrimary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // remove button
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
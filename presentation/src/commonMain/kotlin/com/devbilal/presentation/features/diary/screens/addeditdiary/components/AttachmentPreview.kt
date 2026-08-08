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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
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
    modifier: Modifier = Modifier
) {
    if (attachments.isEmpty()) return

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = attachments,
            key = { it.id.toString() }
        ) { attachment ->
            AttachmentItem(
                attachment = attachment,
                onRemove = { onRemoveAttachment(attachment) }
            )
        }
    }
}

@Composable
private fun AttachmentItem(
    attachment: Attachment,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(SquircleShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surface)
    ) {
        when (attachment) {
            is Attachment.Image -> {
                println("Attachments -> file path = ${attachment.filePath}")
                AsyncImage(
                    model = attachment.filePath,
                    onState = {
                        when (it) {
                            is AsyncImagePainter.State.Success -> println("Attachments -> image loaded, result = ${it.result}")
                            is AsyncImagePainter.State.Empty -> println("Attachments -> image empty")
                            is AsyncImagePainter.State.Error -> println("Attachments -> image error, result = ${it.result.throwable}")
                            is AsyncImagePainter.State.Loading -> println("Attachments -> image loading")
                        }
                    },
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
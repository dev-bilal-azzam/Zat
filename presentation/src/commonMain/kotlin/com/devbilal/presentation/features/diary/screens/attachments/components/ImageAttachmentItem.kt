package com.devbilal.presentation.features.diary.screens.attachments.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun ImageAttachmentItem(
    filePath: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = filePath,
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}

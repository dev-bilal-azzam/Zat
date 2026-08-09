package com.devbilal.presentation.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun VideoPlayer(
    url: String,
    modifier: Modifier = Modifier,
    play: Boolean = false
)

@Composable
expect fun AudioPlayer(
    url: String,
    modifier: Modifier = Modifier,
    play: Boolean = false,
    onProgressUpdate: (Float, Long, Long) -> Unit = { _, _, _ -> }
)

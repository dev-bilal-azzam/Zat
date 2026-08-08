package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable

interface VideoUtils {
    fun generateThumbnail(filePath: String): ByteArray?
}

expect fun getVideoUtils(context: Any? = null): VideoUtils

@Composable
expect fun rememberVideoUtils(): VideoUtils
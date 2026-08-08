package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformFile

interface MediaUtils {
    fun generateThumbnail(filePath: String): ByteArray?
    fun platformFileToTempFile(file: PlatformFile): String
}

expect fun getMediaUtils(context: Any? = null): MediaUtils

@Composable
expect fun rememberMediaUtils(): MediaUtils
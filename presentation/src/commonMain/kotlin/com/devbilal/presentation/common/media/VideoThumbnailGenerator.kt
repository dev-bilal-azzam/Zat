package com.devbilal.presentation.common.media

interface VideoUtils {
    fun generateThumbnail(filePath: String): ByteArray?
}

expect fun getVideoUtils(context: Any? = null): VideoUtils
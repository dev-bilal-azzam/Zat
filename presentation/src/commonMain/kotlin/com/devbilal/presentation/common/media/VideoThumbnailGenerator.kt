package com.devbilal.presentation.common.media

interface VideoUtils {
    fun generateThumbnail(videoBytes: ByteArray?): ByteArray?
}

expect fun getVideoUtils(): VideoUtils
package com.devbilal.presentation.features.diary.screens.addeditdiary.utils

interface VideoUtils {
    fun generateThumbnail(videoBytes: ByteArray?): ByteArray?
}

expect fun getVideoUtils(): VideoUtils
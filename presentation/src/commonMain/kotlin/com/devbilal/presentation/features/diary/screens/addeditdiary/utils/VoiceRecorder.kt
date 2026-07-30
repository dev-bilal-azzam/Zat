package com.devbilal.presentation.features.diary.screens.addeditdiary.utils

import androidx.compose.runtime.Composable

interface VoiceRecorder {
    fun startRecording()
    fun stopRecording()
    fun onResult(callback: (ByteArray) -> Unit)
}

@Composable
expect fun rememberVoiceRecorder(): VoiceRecorder

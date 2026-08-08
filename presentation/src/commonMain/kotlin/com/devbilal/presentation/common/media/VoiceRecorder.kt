package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable

interface VoiceRecorder {
    fun startRecording()
    fun stopRecording()
    fun onResult(callback: (filePath: String) -> Unit)
}

@Composable
expect fun rememberVoiceRecorder(): VoiceRecorder

package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable

interface VoiceRecorder {
    fun startRecording()
    fun pauseRecording()
    fun resumeRecording()
    fun stopRecording()
    fun cancelRecording()
    fun onResult(callback: (filePath: String) -> Unit)
    fun getAmplitude(): Float
}

@Composable
expect fun rememberVoiceRecorder(): VoiceRecorder

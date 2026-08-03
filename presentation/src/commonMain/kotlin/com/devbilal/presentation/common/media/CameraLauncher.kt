package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraLauncher(onResult: (ByteArray?) -> Unit): CameraLauncher

@Composable
expect fun rememberVideoLauncher(onResult: (ByteArray?, ByteArray?) -> Unit): CameraLauncher

interface CameraLauncher {
    fun launch()
}

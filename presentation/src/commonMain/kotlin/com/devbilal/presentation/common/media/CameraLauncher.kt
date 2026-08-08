package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraLauncher(onResult: (String?) -> Unit): CameraLauncher

@Composable
expect fun rememberVideoLauncher(onResult: (String?, ByteArray?) -> Unit): CameraLauncher

interface CameraLauncher {
    fun launch()
}

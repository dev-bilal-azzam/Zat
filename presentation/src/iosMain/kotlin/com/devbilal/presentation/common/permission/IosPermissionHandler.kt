package com.devbilal.presentation.common.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeAudio
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType

class IosPermissionHandler : PermissionHandler {
    override fun isPermissionGranted(permission: Permission): Boolean {
        val mediaType = when (permission) {
            Permission.CAMERA -> AVMediaTypeVideo
            Permission.RECORD_AUDIO -> AVMediaTypeAudio
        }
        return AVCaptureDevice.authorizationStatusForMediaType(mediaType) == AVAuthorizationStatusAuthorized
    }

    override fun askPermission(permission: Permission, onResult: (Boolean) -> Unit) {
        val mediaType = when (permission) {
            Permission.CAMERA -> AVMediaTypeVideo
            Permission.RECORD_AUDIO -> AVMediaTypeAudio
        }
        AVCaptureDevice.requestAccessForMediaType(mediaType) { granted ->
            onResult(granted)
        }
    }
}

@Composable
actual fun rememberPermissionHandler(): PermissionHandler {
    return remember { IosPermissionHandler() }
}

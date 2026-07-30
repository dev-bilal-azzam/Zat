package com.devbilal.presentation.common.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
actual fun rememberPermissionHandler(): PermissionHandler {
    val context = LocalContext.current
    var onResultCallback by remember { mutableStateOf<((Boolean) -> Unit)?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onResultCallback?.invoke(isGranted)
    }

    return remember(context, launcher) {
        object : PermissionHandler {
            override fun isPermissionGranted(permission: Permission): Boolean {
                return ContextCompat.checkSelfPermission(
                    context,
                    permission.toAndroidPermission()
                ) == PackageManager.PERMISSION_GRANTED
            }

            override fun askPermission(permission: Permission, onResult: (Boolean) -> Unit) {
                onResultCallback = onResult
                launcher.launch(permission.toAndroidPermission())
            }
        }
    }
}

private fun Permission.toAndroidPermission(): String = when (this) {
    Permission.CAMERA -> Manifest.permission.CAMERA
    Permission.RECORD_AUDIO -> Manifest.permission.RECORD_AUDIO
}

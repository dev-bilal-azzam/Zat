package com.devbilal.presentation.common.permission

import androidx.compose.runtime.Composable

enum class Permission {
    CAMERA,
    RECORD_AUDIO
}

interface PermissionHandler {
    fun askPermission(permission: Permission, onResult: (Boolean) -> Unit)
    fun isPermissionGranted(permission: Permission): Boolean
}

@Composable
expect fun rememberPermissionHandler(): PermissionHandler

package com.devbilal.presentation.features.diary.screens.addeditdiary.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

class AndroidCameraLauncher(
    private val onResult: (ByteArray?) -> Unit,
    private val launcher: androidx.activity.result.ActivityResultLauncher<Uri>,
    private val fileUri: Uri,
    private val file: File
) : CameraLauncher {
    override fun launch() {
        launcher.launch(fileUri)
    }

    fun handleResult(success: Boolean) {
        if (success) {
            onResult(file.readBytes())
            file.delete()
        } else {
            onResult(null)
        }
    }
}

@Composable
actual fun rememberCameraLauncher(onResult: (ByteArray?) -> Unit): CameraLauncher {
    val context = LocalContext.current
    val photoFile = remember {
        File(context.cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg")
    }
    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
    }

    var cameraLauncher: AndroidCameraLauncher? = null

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        cameraLauncher?.handleResult(success)
    }

    cameraLauncher = remember(photoUri, photoFile) {
        AndroidCameraLauncher(onResult, launcher, photoUri, photoFile)
    }

    return cameraLauncher
}

@Composable
actual fun rememberVideoLauncher(onResult: (ByteArray?) -> Unit): CameraLauncher {
    val context = LocalContext.current
    val videoFile = remember {
        File(context.cacheDir, "temp_video_${System.currentTimeMillis()}.mp4")
    }
    val videoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            videoFile
        )
    }

    var videoLauncher: AndroidCameraLauncher? = null

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        videoLauncher?.handleResult(success)
    }

    videoLauncher = remember(videoUri, videoFile) {
        AndroidCameraLauncher(onResult, launcher, videoUri, videoFile)
    }

    return videoLauncher
}

package com.devbilal.presentation.common.media

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

class AndroidCameraLauncher(
    private val onResult: (String?) -> Unit,
    private val onVideoResult: ((String?, ByteArray?) -> Unit)? = null,
    private val launcher: ActivityResultLauncher<Uri>,
    private val fileUri: Uri,
    private val file: File,
    private val isVideo: Boolean = false
) : CameraLauncher {
    override fun launch() {
        launcher.launch(fileUri)
    }

    fun handleResult(context: Context, success: Boolean) {
        if (success) {
            if (isVideo) {
                val thumbnail = getMediaUtils(context).generateThumbnail(file.path)
                onVideoResult?.invoke(file.path, thumbnail)
            } else {
                onResult(file.path)
            }
            //file.delete()
        } else {
            if (isVideo) onVideoResult?.invoke(null, null) else onResult(null)
        }
    }
}

@Composable
actual fun rememberCameraLauncher(onResult: (String?) -> Unit): CameraLauncher {
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
        cameraLauncher?.handleResult(context, success)
    }

    cameraLauncher = remember(photoUri, photoFile) {
        AndroidCameraLauncher(
            onResult = onResult,
            launcher = launcher,
            fileUri = photoUri,
            file = photoFile
        )
    }

    return cameraLauncher
}

@Composable
actual fun rememberVideoLauncher(onResult: (String?, ByteArray?) -> Unit): CameraLauncher {
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
        videoLauncher?.handleResult(context, success)
    }

    videoLauncher = remember(videoUri, videoFile) {
        AndroidCameraLauncher(
            onResult = {},
            onVideoResult = onResult,
            launcher = launcher,
            fileUri = videoUri,
            file = videoFile,
            isVideo = true
        )
    }

    return videoLauncher
}

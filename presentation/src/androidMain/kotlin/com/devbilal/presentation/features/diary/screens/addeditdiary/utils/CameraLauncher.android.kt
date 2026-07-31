package com.devbilal.presentation.features.diary.screens.addeditdiary.utils

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

class AndroidCameraLauncher(
    private val onResult: (ByteArray?) -> Unit,
    private val onVideoResult: ((ByteArray?, ByteArray?) -> Unit)? = null,
    private val launcher: androidx.activity.result.ActivityResultLauncher<Uri>,
    private val fileUri: Uri,
    private val file: File,
    private val isVideo: Boolean = false
) : CameraLauncher {
    override fun launch() {
        launcher.launch(fileUri)
    }

    fun handleResult(success: Boolean) {
        if (success) {
            val bytes = file.readBytes()
            if (isVideo) {
                val thumbnail = generateVideoThumbnail(file)
                onVideoResult?.invoke(bytes, thumbnail)
            } else {
                onResult(bytes)
            }
            file.delete()
        } else {
            if (isVideo) onVideoResult?.invoke(null, null) else onResult(null)
        }
    }

    private fun generateVideoThumbnail(file: File): ByteArray? {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(file.absolutePath)
            val bitmap = retriever.getFrameAtTime(1000000) // 1 second
            val stream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            stream.toByteArray()
        } catch (e: Exception) {
            null
        } finally {
            retriever.release()
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
actual fun rememberVideoLauncher(onResult: (ByteArray?, ByteArray?) -> Unit): CameraLauncher {
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

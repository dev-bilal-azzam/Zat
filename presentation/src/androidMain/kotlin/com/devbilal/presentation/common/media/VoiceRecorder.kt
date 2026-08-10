package com.devbilal.presentation.common.media

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.File

class AndroidVoiceRecorder(private val context: Context) : VoiceRecorder {
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null
    private var onResultCallback: ((String) -> Unit)? = null

    override fun startRecording() {
        val file = File(context.cacheDir, "temp_recording_${System.currentTimeMillis()}.m4a")
        outputFile = file

        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file.absolutePath)
            prepare()
            start()
        }
    }

    override fun pauseRecording() {
        mediaRecorder?.pause()
    }

    override fun resumeRecording() {
        mediaRecorder?.resume()
    }

    override fun stopRecording() {
        try {
            mediaRecorder?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaRecorder?.release()
            mediaRecorder = null
        }

        outputFile?.let { file ->
            if (file.exists() && file.length() > 0) {
                onResultCallback?.invoke(file.absolutePath)
            }
        }
    }

    override fun cancelRecording() {
        try {
            mediaRecorder?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaRecorder?.release()
            mediaRecorder = null
        }
        outputFile?.delete()
        outputFile = null
    }

    override fun onResult(callback: (String) -> Unit) {
        onResultCallback = callback
    }

    override fun getAmplitude(): Float {
        val max = mediaRecorder?.maxAmplitude ?: 0
        // Normalize 0..32767 to 0..1
        return (max.toFloat() / 32767f).coerceIn(0f, 1f)
    }
}

@Composable
actual fun rememberVoiceRecorder(): VoiceRecorder {
    val context = LocalContext.current
    return remember { AndroidVoiceRecorder(context) }
}

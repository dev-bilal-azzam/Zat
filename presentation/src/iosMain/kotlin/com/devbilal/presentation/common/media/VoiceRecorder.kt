@file:OptIn(ExperimentalForeignApi::class)

package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.*
import platform.CoreAudioTypes.kAudioFormatMPEG4AAC
import platform.Foundation.*
import kotlin.math.pow

@OptIn(ExperimentalForeignApi::class)
class IosVoiceRecorder : VoiceRecorder {
    private var recorder: AVAudioRecorder? = null
    private val audioSession = AVAudioSession.sharedInstance()
    private var onResultCallback: ((String) -> Unit)? = null
    private var outputFileUrl: NSURL? = null

    override fun startRecording() {
        val fileManager = NSFileManager.defaultManager
        val cachesDir =
            fileManager.URLsForDirectory(NSCachesDirectory, NSUserDomainMask).first() as NSURL
        val fileName = "temp_recording_${NSDate().timeIntervalSince1970.toLong()}.m4a"
        outputFileUrl = cachesDir.URLByAppendingPathComponent(fileName)

        val settings = mapOf<Any?, Any?>(
            AVFormatIDKey to NSNumber(kAudioFormatMPEG4AAC.toInt()),
            AVSampleRateKey to NSNumber(44100.0),
            AVNumberOfChannelsKey to NSNumber(1),
            AVEncoderAudioQualityKey to NSNumber(AVAudioQualityMedium.toInt())
        )

        audioSession.setCategory(AVAudioSessionCategoryPlayAndRecord, error = null)
        audioSession.setActive(true, error = null)

        val url = outputFileUrl ?: return
        recorder = AVAudioRecorder(url, settings, null)
        recorder?.meteringEnabled = true
        recorder?.prepareToRecord()
        recorder?.record()
    }

    override fun pauseRecording() {
        recorder?.pause()
    }

    override fun resumeRecording() {
        recorder?.record()
    }

    override fun stopRecording() {
        recorder?.stop()
        audioSession.setActive(false, error = null)

        outputFileUrl?.path?.let { filePath ->
            if (NSFileManager.defaultManager.fileExistsAtPath(filePath)) {
                onResultCallback?.invoke(filePath)
            }
        }
        recorder = null
    }

    override fun cancelRecording() {
        recorder?.stop()
        audioSession.setActive(false, error = null)
        outputFileUrl?.path?.let { filePath ->
            NSFileManager.defaultManager.removeItemAtPath(filePath, error = null)
        }
        recorder = null
    }

    override fun onResult(callback: (String) -> Unit) {
        onResultCallback = callback
    }

    override fun getAmplitude(): Float {
        recorder?.updateMeters()
        val power = recorder?.averagePowerForChannel(0UL) ?: -160f
        // Convert dB to linear scale 0..1
        // -160dB is silence, 0dB is max
        return 10.0.pow(power.toDouble() / 20.0).toFloat()
    }
}

@Composable
actual fun rememberVoiceRecorder(): VoiceRecorder = remember { IosVoiceRecorder() }

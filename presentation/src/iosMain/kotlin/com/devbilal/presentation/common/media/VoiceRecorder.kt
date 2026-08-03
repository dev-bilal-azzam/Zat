@file:OptIn(ExperimentalForeignApi::class)

package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.AVFAudio.AVAudioQualityMedium
import platform.AVFAudio.AVAudioRecorder
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayAndRecord
import platform.AVFAudio.AVEncoderAudioQualityKey
import platform.AVFAudio.AVFormatIDKey
import platform.AVFAudio.AVNumberOfChannelsKey
import platform.AVFAudio.AVSampleRateKey
import platform.AVFAudio.setActive
import platform.CoreAudioTypes.kAudioFormatMPEG4AAC
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSFileManager
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.timeIntervalSince1970
import platform.posix.memcpy

class IosVoiceRecorder : VoiceRecorder {
    private var recorder: AVAudioRecorder? = null
    private val audioSession = AVAudioSession.sharedInstance()
    private var onResultCallback: ((ByteArray) -> Unit)? = null
    private var outputFileUrl: NSURL? = null

    override fun startRecording() {
        val fileManager = NSFileManager.defaultManager
        val cachesDir =
            fileManager.URLsForDirectory(NSCachesDirectory, NSUserDomainMask).first() as NSURL
        val fileName = "temp_recording_${NSDate().timeIntervalSince1970}.m4a"
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
        recorder?.prepareToRecord()
        recorder?.record()
    }

    override fun stopRecording() {
        recorder?.stop()
        audioSession.setActive(false, error = null)

        outputFileUrl?.let { url ->
            val data = NSData.dataWithContentsOfURL(url)
            if (data != null) {
                val bytes = ByteArray(data.length.toInt())
                if (data.length > 0u) {
                    data.bytes?.let { ptr ->
                        bytes.usePinned { pinned ->
                            memcpy(pinned.addressOf(0), ptr, data.length)
                        }
                    }
                    onResultCallback?.invoke(bytes)
                }
            }
            NSFileManager.defaultManager.removeItemAtURL(url, null)
        }
        recorder = null
    }

    override fun onResult(callback: (ByteArray) -> Unit) {
        onResultCallback = callback
    }
}

@Composable
actual fun rememberVoiceRecorder(): VoiceRecorder = remember { IosVoiceRecorder() }

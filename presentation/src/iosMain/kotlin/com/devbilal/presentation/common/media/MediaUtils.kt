@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.posix.memcpy

object IosThumbnailBridge {
    var generateThumbNative: ((String) -> NSData?)? = null
}

class IosMediaUtils : MediaUtils {
    override fun generateThumbnail(filePath: String): ByteArray? {
        val nsData = IosThumbnailBridge.generateThumbNative?.invoke(filePath) ?: return null

        val byteArray = ByteArray(nsData.length.toInt())
        byteArray.usePinned { pinned ->
            memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
        }

        return byteArray
    }

    override fun platformFileToTempFile(file: PlatformFile) =
        file.path ?: throw IllegalStateException("File path is null on iOS")

}

actual fun getMediaUtils(context: Any?): MediaUtils = IosMediaUtils()

@Composable
actual fun rememberMediaUtils(): MediaUtils = IosMediaUtils()
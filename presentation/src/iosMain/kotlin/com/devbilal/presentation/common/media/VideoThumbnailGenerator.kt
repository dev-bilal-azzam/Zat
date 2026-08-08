@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.devbilal.presentation.common.media

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import platform.AVFoundation.AVAssetImageGenerator
import platform.CoreMedia.CMTime
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation


class IosVideoUtils : VideoUtils {
    override fun generateThumbnail(filePath: String): ByteArray? {
        memScoped {
            return try {
                val cleanPath = filePath.replace("file://", "")
                val url = NSURL.fileURLWithPath(cleanPath)
                val asset = platform.AVFoundation.AVAsset.assetWithURL(url)
                val generator = AVAssetImageGenerator(asset).apply {
                    appliesPreferredTrackTransform = true
                }

                val time = CMTimeMake(1, 1)
                val actualTime = alloc<CMTime>()
                val error = alloc<ObjCObjectVar<NSError?>>()

                val cgImage = generator.copyCGImageAtTime(time, actualTime.ptr, error.ptr)

                NSFileManager.defaultManager.removeItemAtURL(url, null)

                if (cgImage != null) {
                    val uiImage = UIImage.imageWithCGImage(cgImage)
                    val imageData = UIImageJPEGRepresentation(uiImage, 1.0)

                    imageData?.let { nsData ->
                        nsData.bytes?.readBytes(nsData.length.toInt())
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                println("iOS Thumbnail Extraction failed: ${e.message}")
                null
            }
        }
    }

}


actual fun getVideoUtils(context: Any?): VideoUtils = IosVideoUtils()
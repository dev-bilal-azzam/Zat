@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.devbilal.presentation.features.diary.screens.addeditdiary.utils

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.usePinned
import platform.AVFoundation.AVAssetImageGenerator
import platform.AVFoundation.AVURLAsset
import platform.CoreMedia.CMTime
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.create
import platform.Foundation.writeToURL
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation


class IosVideoUtils : VideoUtils {
    override fun generateThumbnail(videoBytes: ByteArray?): ByteArray? {
        memScoped {
            return try {
                if (videoBytes == null) return null

                val data = videoBytes.usePinned { pinned ->
                    NSData.create(
                        bytes = pinned.addressOf(0),
                        length = videoBytes.size.toULong()
                    )
                }

                val tempPath = NSTemporaryDirectory() + NSUUID().UUIDString + ".mp4"
                val fileUrl = NSURL.fileURLWithPath(tempPath)
                data.writeToURL(fileUrl, atomically = true)

                val asset = AVURLAsset(fileUrl, null)
                val generator = AVAssetImageGenerator(asset).apply {
                    appliesPreferredTrackTransform = true
                }

                val time = CMTimeMake(1, 1)
                val actualTime = alloc<CMTime>()
                val error = alloc<ObjCObjectVar<NSError?>>()

                val cgImage = generator.copyCGImageAtTime(time, actualTime.ptr, error.ptr)

                NSFileManager.defaultManager.removeItemAtURL(fileUrl, null)

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


actual fun getVideoUtils(): VideoUtils = IosVideoUtils()
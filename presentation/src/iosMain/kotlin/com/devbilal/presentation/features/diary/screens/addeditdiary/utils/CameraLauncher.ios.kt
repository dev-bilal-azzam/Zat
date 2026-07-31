@file:OptIn(ExperimentalForeignApi::class)

package com.devbilal.presentation.features.diary.screens.addeditdiary.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.AVFoundation.*
import platform.CoreMedia.*
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerMediaURL
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIWindow
import platform.darwin.NSObject
import platform.posix.memcpy

class IosCameraLauncher(
    private val onResult: (ByteArray?) -> Unit,
    private val onVideoResult: ((ByteArray?, ByteArray?) -> Unit)? = null,
    private val isVideo: Boolean
) : CameraLauncher, NSObject(), UIImagePickerControllerDelegateProtocol,
    UINavigationControllerDelegateProtocol {

    override fun launch() {
        val window = UIApplication.sharedApplication.keyWindow
            ?: UIApplication.sharedApplication.windows.firstOrNull() as? UIWindow
        val rootViewController = window?.rootViewController ?: return

        if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
            val picker = UIImagePickerController().apply {
                sourceType =
                    UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                mediaTypes = if (isVideo) listOf("public.movie") else listOf("public.image")
                delegate = this@IosCameraLauncher
            }
            rootViewController.presentViewController(picker, animated = true, completion = null)
        } else {
            onResult(null)
        }
    }

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        if (isVideo) {
            val videoUrl = didFinishPickingMediaWithInfo[UIImagePickerControllerMediaURL] as? NSURL
            val videoData = videoUrl?.let { NSData.dataWithContentsOfURL(it) }
            val videoBytes = videoData?.let {
                ByteArray(it.length.toInt()).apply {
                    if (it.length > 0u) {
                        it.bytes?.let { ptr ->
                            usePinned { pinned ->
                                memcpy(pinned.addressOf(0), ptr, it.length)
                            }
                        }
                    }
                }
            }
            val thumbnailBytes = videoUrl?.let { generateVideoThumbnail(it) }
            onVideoResult?.invoke(videoBytes, thumbnailBytes)
        } else {
            val image =
                didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
            val data = image?.let { UIImageJPEGRepresentation(it, 0.8) }
            val bytes = data?.let {
                ByteArray(it.length.toInt()).apply {
                    if (it.length > 0u) {
                        it.bytes?.let { ptr ->
                            usePinned { pinned ->
                                memcpy(pinned.addressOf(0), ptr, it.length)
                            }
                        }
                    }
                }
            }
            onResult(bytes)
        }
        picker.dismissViewControllerAnimated(true, completion = null)
    }

    private fun generateVideoThumbnail(url: NSURL): ByteArray? {
        val asset = AVAsset.assetWithURL(url)
        val generator = AVAssetImageGenerator(asset = asset).apply {
            appliesPreferredTrackTransform = true
        }
        val time = CMTimeMake(value = 1, timescale = 1)
        val imageRef = generator.copyCGImageAtTime(time, actualTime = null, error = null) ?: return null
        val uiImage = UIImage.imageWithCGImage(imageRef)
        val data = UIImageJPEGRepresentation(uiImage, 0.8) ?: return null
        return ByteArray(data.length.toInt()).apply {
            if (data.length > 0u) {
                data.bytes?.let { ptr ->
                    usePinned { pinned ->
                        memcpy(pinned.addressOf(0), ptr, data.length)
                    }
                }
            }
        }
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        onResult(null)
        picker.dismissViewControllerAnimated(true, completion = null)
    }
}

@Composable
actual fun rememberCameraLauncher(onResult: (ByteArray?) -> Unit): CameraLauncher =
    remember(onResult) { IosCameraLauncher(onResult, isVideo = false) }

@Composable
actual fun rememberVideoLauncher(onResult: (ByteArray?, ByteArray?) -> Unit): CameraLauncher =
    remember(onResult) { IosCameraLauncher(onResult = {}, onVideoResult = onResult, isVideo = true) }

@file:OptIn(ExperimentalForeignApi::class)

package com.devbilal.presentation.common.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.writeToURL
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

class IosCameraLauncher(
    private val onResult: (String?) -> Unit,
    private val onVideoResult: ((filePath: String?, thumbnail: ByteArray?) -> Unit)? = null,
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
            if (isVideo) onVideoResult?.invoke(null, null) else onResult(null)
        }
    }

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        if (isVideo) {
            val videoUrl = didFinishPickingMediaWithInfo[UIImagePickerControllerMediaURL] as? NSURL
            val videoPath = videoUrl?.path

            if (videoPath != null) {
                val thumbnailBytes = getVideoUtils().generateThumbnail(videoPath)
                onVideoResult?.invoke(videoPath, thumbnailBytes)
            } else {
                onVideoResult?.invoke(null, null)
            }
        } else {
            val image =
                didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
            val data = image?.let { UIImageJPEGRepresentation(it, 0.8) }

            val imagePath = data?.let { nsData ->
                if (nsData.length > 0u) {
                    saveImageToTempFile(nsData)
                } else null
            }

            onResult(imagePath)
        }
        picker.dismissViewControllerAnimated(true, completion = null)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        if (isVideo) onVideoResult?.invoke(null, null) else onResult(null)
        picker.dismissViewControllerAnimated(true, completion = null)
    }

    private fun saveImageToTempFile(data: NSData): String? {
        val fileName = "captured_image_${NSDate().timeIntervalSince1970.toLong()}.jpg"
        val tempDir = NSTemporaryDirectory()
        val filePath = "$tempDir$fileName"
        val fileUrl = NSURL.fileURLWithPath(filePath)

        return if (data.writeToURL(fileUrl, true)) {
            filePath
        } else {
            null
        }
    }
}

@Composable
actual fun rememberCameraLauncher(onResult: (String?) -> Unit): CameraLauncher =
    remember(onResult) { IosCameraLauncher(onResult, isVideo = false) }

@Composable
actual fun rememberVideoLauncher(onResult: (String?, ByteArray?) -> Unit): CameraLauncher =
    remember(onResult) {
        IosCameraLauncher(
            onResult = {},
            onVideoResult = onResult,
            isVideo = true
        )
    }

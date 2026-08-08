package com.devbilal.presentation.common.media

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.core.net.toUri
import java.io.ByteArrayOutputStream

class AndroidVideoUtils(private val context: Context) : VideoUtils {
    override fun generateThumbnail(filePath: String): ByteArray? {
        val retriever = MediaMetadataRetriever()
        return try {
            if (filePath.startsWith("content://")) {
                retriever.setDataSource(context, filePath.toUri())
            } else {
                retriever.setDataSource(filePath)
            }

            val bitmap = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)

            bitmap?.let {
                val stream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.toByteArray()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            retriever.release()
        }
    }
}

actual fun getVideoUtils(context: Any?): VideoUtils {
    requireNotNull(context) { "Context is required on Android to initialize VideoUtils" }
    return AndroidVideoUtils(context as Context)
}

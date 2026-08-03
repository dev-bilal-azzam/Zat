package com.devbilal.presentation.common.media

import android.graphics.Bitmap
import android.media.MediaDataSource
import android.media.MediaMetadataRetriever
import java.io.ByteArrayOutputStream

class AndroidVideoUtils : VideoUtils {
    override fun generateThumbnail(videoBytes: ByteArray?): ByteArray? {
        val retriever = MediaMetadataRetriever()
        return try {
            if (videoBytes == null) {
                retriever.release()
                return null
            }
            retriever.setDataSource(object : MediaDataSource() {
                override fun readAt(
                    position: Long,
                    buffer: ByteArray,
                    offset: Int,
                    size: Int
                ): Int {
                    if (position >= videoBytes.size) return -1
                    val bytesToRead = minOf(size, (videoBytes.size - position).toInt())
                    System.arraycopy(videoBytes, position.toInt(), buffer, offset, bytesToRead)
                    return bytesToRead
                }

                override fun getSize(): Long = videoBytes.size.toLong()
                override fun close() {}
            })

            val bitmap =
                retriever.getFrameAtTime(1_000_000L, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)

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

actual fun getVideoUtils(): VideoUtils = AndroidVideoUtils()

package com.devbilal.presentation.common.media

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import io.github.vinceglb.filekit.core.PlatformFile
import java.io.ByteArrayOutputStream
import java.io.File

class AndroidMediaUtils(private val context: Context) : MediaUtils {
    override fun generateThumbnail(filePath: String): ByteArray? {
        val retriever = MediaMetadataRetriever()
        return try {
            if (filePath.startsWith("content://")) {
                retriever.setDataSource(context, filePath.toUri())
            } else {
                retriever.setDataSource(filePath)
            }

            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val durationMs = durationStr?.toLongOrNull() ?: 0L

            val midPointUs = if (durationMs > 0) (durationMs / 2) * 1000L else 1_000_000L

            val bitmap = retriever.getFrameAtTime(
                midPointUs,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
            )

            bitmap?.let {
                val stream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                stream.toByteArray()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            retriever.release()
        }
    }

    override fun platformFileToTempFile(file: PlatformFile): String {
        val extension = file.name.substringAfterLast('.', "tmp")
        val tempFile = File(context.cacheDir, "picked_${System.currentTimeMillis()}.$extension")

        context.contentResolver.openInputStream(file.uri)?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return tempFile.absolutePath
    }
}

actual fun getMediaUtils(context: Any?): MediaUtils {
    requireNotNull(context) { "Context is required on Android to initialize VideoUtils" }
    return AndroidMediaUtils(context as Context)
}

@Composable
actual fun rememberMediaUtils(): MediaUtils {
    val context = LocalContext.current
    return AndroidMediaUtils(context)
}

package com.devbilal.data.util

import android.content.Context
import java.io.File
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidFileManager(private val context: Context) : FileManager {
    private val attachmentDir = File(context.filesDir, "attachments").apply {
        if (!exists()) mkdirs()
    }

    override suspend fun saveFile(fileName: String, bytes: ByteArray): String = withContext(Dispatchers.IO) {
        val file = File(attachmentDir, fileName)
        file.writeBytes(bytes)
        file.absolutePath
    }

    override suspend fun copyFile(sourceFilePath: String, fileName: String): String = withContext(Dispatchers.IO) {
        val destFile = File(attachmentDir, fileName)
        if (sourceFilePath == destFile.absolutePath) return@withContext destFile.absolutePath

        if (sourceFilePath.startsWith("content://")) {
            val uri = Uri.parse(sourceFilePath)
            context.contentResolver.openInputStream(uri)?.use { input ->
                destFile.outputStream().use { output -> input.copyTo(output) }
            }
        } else {
            val sourceFile = File(sourceFilePath)
            if (sourceFile.exists()) {
                sourceFile.copyTo(destFile, overwrite = true)
            }
        }
        destFile.absolutePath
    }

    override suspend fun readFile(filePath: String): ByteArray? = withContext(Dispatchers.IO) {
        val file = File(filePath)
        if (file.exists()) file.readBytes() else null
    }

    override suspend fun deleteFile(filePath: String): Boolean = withContext(Dispatchers.IO) {
        val file = File(filePath)
        if (file.exists()) file.delete() else false
    }

    override suspend fun clearTempCache(): Unit = withContext(Dispatchers.IO) {
        context.cacheDir.listFiles()?.forEach { file ->
            if (file.isFile) file.delete()
        }
    }
}
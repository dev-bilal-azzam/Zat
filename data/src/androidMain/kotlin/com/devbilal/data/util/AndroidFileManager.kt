package com.devbilal.data.util

import android.content.Context
import java.io.File

class AndroidFileManager(private val context: Context) : FileManager {
    private val attachmentDir = File(context.filesDir, "attachments").apply { 
        if (!exists()) mkdirs() 
    }

    override suspend fun saveFile(fileName: String, bytes: ByteArray): String {
        val file = File(attachmentDir, fileName)
        file.writeBytes(bytes)
        return file.absolutePath
    }

    override suspend fun readFile(filePath: String): ByteArray? {
        val file = File(filePath)
        return if (file.exists()) file.readBytes() else null
    }

    override suspend fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.exists()) file.delete() else false
    }
}

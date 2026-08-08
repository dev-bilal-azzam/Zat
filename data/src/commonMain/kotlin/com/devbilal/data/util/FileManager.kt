package com.devbilal.data.util

interface FileManager {
    suspend fun saveFile(fileName: String, bytes: ByteArray): String
    suspend fun copyFile(sourceFilePath: String, fileName: String): String
    suspend fun readFile(filePath: String): ByteArray?
    suspend fun deleteFile(filePath: String): Boolean
}

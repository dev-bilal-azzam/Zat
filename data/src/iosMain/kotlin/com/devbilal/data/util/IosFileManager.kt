package com.devbilal.data.util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithBytes
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.writeToURL
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
class IosFileManager : FileManager {
    private val fileManager = NSFileManager.defaultManager
    private val documentsDir =
        fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as NSURL
    private val attachmentDir = documentsDir.URLByAppendingPathComponent("attachments")!!

    init {
        if (!fileManager.fileExistsAtPath(attachmentDir.path!!)) {
            fileManager.createDirectoryAtURL(
                attachmentDir,
                withIntermediateDirectories = true,
                attributes = null,
                error = null
            )
        }
    }

    override suspend fun saveFile(fileName: String, bytes: ByteArray): String {
        val fileUrl = attachmentDir.URLByAppendingPathComponent(fileName)!!
        val data = bytes.usePinned { NSData.dataWithBytes(it.addressOf(0), bytes.size.toULong()) }
        data.writeToURL(fileUrl, atomically = true)
        return fileUrl.path!!
    }

    override suspend fun copyFile(sourceFilePath: String, fileName: String): String {
        val destUrl = attachmentDir.URLByAppendingPathComponent(fileName)!!
        val destPath = destUrl.path!!
        val cleanSourcePath = sourceFilePath.removePrefix("file://")

        if (cleanSourcePath == destPath) return destPath

        if (fileManager.fileExistsAtPath(destPath)) {
            fileManager.removeItemAtPath(destPath, error = null)
        }
        fileManager.copyItemAtPath(cleanSourcePath, destPath, error = null)
        return destPath
    }

    override suspend fun readFile(filePath: String): ByteArray? {
        val data = NSData.dataWithContentsOfFile(filePath) ?: return null
        val bytes = ByteArray(data.length.toInt())
        bytes.usePinned { memcpy(it.addressOf(0), data.bytes, data.length) }
        return bytes
    }

    override suspend fun deleteFile(filePath: String): Boolean {
        return fileManager.removeItemAtPath(filePath, error = null)
    }

    override suspend fun clearTempCache() {
        val tempDir = NSTemporaryDirectory()
        val files = fileManager.contentsOfDirectoryAtPath(tempDir, null) as? List<String>
        files?.forEach { fileName ->
            fileManager.removeItemAtPath("$tempDir$fileName", null)
        }
    }
}

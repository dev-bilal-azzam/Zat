package com.devbilal.data.util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
class IosFileManager : FileManager {
    private val fileManager = NSFileManager.defaultManager
    private val documentsDir = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as NSURL
    private val attachmentDir = documentsDir.URLByAppendingPathComponent("attachments")!!

    init {
        if (!fileManager.fileExistsAtPath(attachmentDir.path!!)) {
            fileManager.createDirectoryAtURL(attachmentDir, withIntermediateDirectories = true, attributes = null, error = null)
        }
    }

    override suspend fun saveFile(fileName: String, bytes: ByteArray): String {
        val fileUrl = attachmentDir.URLByAppendingPathComponent(fileName)!!
        val data = bytes.usePinned { 
            NSData.dataWithBytes(it.addressOf(0), bytes.size.toULong())
        }
        data.writeToURL(fileUrl, true)
        return fileUrl.path!!
    }

    override suspend fun readFile(filePath: String): ByteArray? {
        val data = NSData.dataWithContentsOfFile(filePath) ?: return null
        val bytes = ByteArray(data.length.toInt())
        bytes.usePinned { 
            memcpy(it.addressOf(0), data.bytes, data.length)
        }
        return bytes
    }

    override suspend fun deleteFile(filePath: String): Boolean {
        return fileManager.removeItemAtPath(filePath, null)
    }
}

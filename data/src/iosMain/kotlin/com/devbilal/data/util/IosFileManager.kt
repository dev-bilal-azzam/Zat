package com.devbilal.data.util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.posix.memcpy
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.reinterpret

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

        // Prevent unnecessary self-copying
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

    override suspend fun calculateHash(filePath: String): String {
        val data = NSData.dataWithContentsOfFile(filePath) ?: return ""
        val hash = ByteArray(CC_SHA256_DIGEST_LENGTH)
        hash.usePinned { pinned ->
            CC_SHA256(data.bytes, data.length.toUInt(), pinned.addressOf(0).reinterpret<UByteVar>())
        }
        return hash.joinToString("") { (it.toInt() and 0xFF).toString(16).padStart(2, '0') }
    }

    override suspend fun getFileSize(filePath: String): Long {
        val attributes = fileManager.attributesOfItemAtPath(filePath, null) ?: return 0L
        return (attributes[NSFileSize] as? NSNumber)?.longLongValue ?: 0L
    }

    override fun getAttachmentPath(fileName: String): String {
        return attachmentDir.URLByAppendingPathComponent(fileName)!!.path!!
    }
}

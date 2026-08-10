package com.devbilal.data.util

/**
 * Platform-agnostic interface for file system operations.
 */
interface FileManager {
    /** Saves raw bytes to a file in the app's internal storage. */
    suspend fun saveFile(fileName: String, bytes: ByteArray): String
    
    /** Copies a file from an external source (uri/path) to the app's internal storage. */
    suspend fun copyFile(sourceFilePath: String, fileName: String): String
    
    /** Reads a file from disk into a ByteArray. */
    suspend fun readFile(filePath: String): ByteArray?
    
    /** Deletes a physical file from the disk. */
    suspend fun deleteFile(filePath: String): Boolean
    
    /** Clears temporary cache directories. */
    suspend fun clearTempCache()
    
    /** Calculates a SHA-256 hash for the file at the given path. Used for deduplication. */
    suspend fun calculateHash(filePath: String): String
    
    /** Returns the size of the file in bytes. */
    suspend fun getFileSize(filePath: String): Long
    
    /** Returns the absolute path for a filename within the internal attachment directory. */
    fun getAttachmentPath(fileName: String): String
}

@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.datasource.local.database.attachment

import com.devbilal.domain.entity.Attachment
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Maps an [AttachmentDto] from the database to a domain-layer [Attachment] object.
 */
fun AttachmentDto.toDomain(thumbnail: ByteArray? = null): Attachment {
    // SHA-256 hash is 64 hex characters. 
    // We take the first 32 characters to form a valid UUID string (128 bits) deterministically.
    val hex = hash.take(32).padStart(32, '0')
    val uuidString = "${hex.substring(0, 8)}-${hex.substring(8, 12)}-${hex.substring(12, 16)}-${hex.substring(16, 20)}-${hex.substring(20, 32)}"
    val id = Uuid.parse(uuidString)

    return when (type) {
        "IMAGE" -> Attachment.Image(
            id = id,
            filePath = filePath
        )
        "VIDEO" -> Attachment.Video(
            id = id,
            filePath = filePath,
            thumbnail = thumbnail ?: byteArrayOf()
        )
        "AUDIO" -> Attachment.Audio(
            id = id,
            filePath = filePath
        )
        else -> throw IllegalArgumentException("Unknown attachment type: ${type}")
    }
}

/**
 * Maps a domain-layer [Attachment] to an [AttachmentDto] for database storage.
 */
fun Attachment.toEntity(hash: String, filePath: String, size: Long, thumbnailHash: String? = null): AttachmentDto {
    return AttachmentDto(
        hash = hash,
        extension = filePath.substringAfterLast('.', ""),
        type = type.name,
        size = size,
        filePath = filePath,
        thumbnailHash = thumbnailHash
    )
}

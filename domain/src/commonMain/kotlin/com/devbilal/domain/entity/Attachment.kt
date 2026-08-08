@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface Attachment {
    val id: Uuid
    val filePath: String
    val type: AttachmentType

    data class Image(
        override val id: Uuid = Uuid.random(),
        override val filePath: String
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.IMAGE
    }

    data class Video(
        override val id: Uuid = Uuid.random(),
        override val filePath: String,
        val thumbnail: ByteArray
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.VIDEO

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Video) return false
            if (id != other.id) return false
            if (filePath != other.filePath) return false
            if (!thumbnail.contentEquals(other.thumbnail)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + thumbnail.contentHashCode()
            return result
        }
    }

    data class Audio(
        override val id: Uuid = Uuid.random(),
        override val filePath: String
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.AUDIO
    }
}

enum class AttachmentType {
    IMAGE, VIDEO, AUDIO
}

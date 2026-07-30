@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface Attachment {
    val id: Uuid
    val bytes: ByteArray
    val type: AttachmentType

    data class Image(
        override val id: Uuid = Uuid.random(),
        override val bytes: ByteArray
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.IMAGE

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Image) return false
            if (id != other.id) return false
            if (!bytes.contentEquals(other.bytes)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + bytes.contentHashCode()
            return result
        }
    }

    data class Video(
        override val id: Uuid = Uuid.random(),
        override val bytes: ByteArray
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.VIDEO

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Video) return false
            if (id != other.id) return false
            if (!bytes.contentEquals(other.bytes)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + bytes.contentHashCode()
            return result
        }
    }

    data class Audio(
        override val id: Uuid = Uuid.random(),
        override val bytes: ByteArray
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.AUDIO

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Audio) return false
            if (id != other.id) return false
            if (!bytes.contentEquals(other.bytes)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + bytes.contentHashCode()
            return result
        }
    }
}

enum class AttachmentType {
    IMAGE, VIDEO, AUDIO
}

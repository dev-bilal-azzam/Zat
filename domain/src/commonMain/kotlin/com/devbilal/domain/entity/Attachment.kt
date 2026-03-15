@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface Attachment {
    val id: Uuid
    val path: String
    val type: AttachmentType

    data class Image(
        override val id: Uuid = Uuid.random(),
        override val path: String
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.IMAGE
    }

    data class Video(
        override val id: Uuid = Uuid.random(),
        override val path: String
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.VIDEO
    }

    data class Audio(
        override val id: Uuid = Uuid.random(),
        override val path: String
    ) : Attachment {
        override val type: AttachmentType = AttachmentType.AUDIO
    }
}

enum class AttachmentType {
    IMAGE, VIDEO, AUDIO
}

package com.devbilal.data.datasource.local.database.diaryentry

import kotlinx.serialization.Serializable

@Serializable
sealed interface AttachmentDto {
    val id: String
    val path: String
    val type: String

    @Serializable
    data class Image(
        override val id: String,
        override val path: String,
        override val type: String = "IMAGE"
    ) : AttachmentDto

    @Serializable
    data class Video(
        override val id: String,
        override val path: String,
        override val type: String = "VIDEO"
    ) : AttachmentDto

    @Serializable
    data class Audio(
        override val id: String,
        override val path: String,
        override val type: String = "AUDIO"
    ) : AttachmentDto
}

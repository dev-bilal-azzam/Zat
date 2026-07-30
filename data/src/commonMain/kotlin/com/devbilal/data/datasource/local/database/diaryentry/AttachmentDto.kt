package com.devbilal.data.datasource.local.database.diaryentry

import kotlinx.serialization.Serializable

@Serializable
sealed interface AttachmentDto {
    val id: String
    val filePath: String

    @Serializable
    data class Image(
        override val id: String,
        override val filePath: String,
    ) : AttachmentDto

    @Serializable
    data class Video(
        override val id: String,
        override val filePath: String,
    ) : AttachmentDto

    @Serializable
    data class Audio(
        override val id: String,
        override val filePath: String,
    ) : AttachmentDto
}

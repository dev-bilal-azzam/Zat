package com.devbilal.data.datasource.local.database.attachment

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDto
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryVersionDto

@Entity(tableName = "attachments")
data class AttachmentDto(
    @PrimaryKey val hash: String,
    val extension: String,
    val type: String,
    val size: Long,
    val filePath: String,
    val thumbnailHash: String? = null
)

@Entity(
    tableName = "diary_entry_attachment_cross_ref",
    primaryKeys = ["entryId", "attachmentHash"],
    foreignKeys = [
        ForeignKey(
            entity = DiaryEntryDto::class,
            parentColumns = ["id"],
            childColumns = ["entryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AttachmentDto::class,
            parentColumns = ["hash"],
            childColumns = ["attachmentHash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DiaryEntryAttachmentCrossRef(
    val entryId: String,
    val attachmentHash: String
)

@Entity(
    tableName = "diary_version_attachment_cross_ref",
    primaryKeys = ["versionId", "attachmentHash"],
    foreignKeys = [
        ForeignKey(
            entity = DiaryVersionDto::class,
            parentColumns = ["id"],
            childColumns = ["versionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AttachmentDto::class,
            parentColumns = ["hash"],
            childColumns = ["attachmentHash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DiaryVersionAttachmentCrossRef(
    val versionId: String,
    val attachmentHash: String
)

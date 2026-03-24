package com.devbilal.data.datasource.local.database.diaryhistory

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.devbilal.data.datasource.local.database.diaryentry.AttachmentDto
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDto

@Entity(
    tableName = "diary_versions",
    foreignKeys = [
        ForeignKey(
            entity = DiaryEntryDto::class,
            parentColumns = ["id"],
            childColumns = ["primaryEntryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["primaryEntryId"])]
)
data class DiaryVersionDto(
    @PrimaryKey val id: String,
    val primaryEntryId: String,
    val title: String,
    val content: String,
    val date: String,
    val createdAt: String,
    val color: Long,
    val attachments: List<AttachmentDto>,
    val versionCreatedAt: String
)

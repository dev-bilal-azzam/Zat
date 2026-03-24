package com.devbilal.data.datasource.local.database.diaryentry

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entries")
data class DiaryEntryDto(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val date: String,
    val createdAt: String,
    val color: Long,
    val attachments: List<AttachmentDto>
)

package com.devbilal.data.datasource.local.database.diaryhistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_diary_versions")
data class DeletedDiaryVersionDto(
    @PrimaryKey val id: String,
    val primaryEntryId: String,
    val title: String,
    val content: String,
    val date: String,
    val createdAt: String,
    val color: Long,
    val versionCreatedAt: String,
    val deletedAt: String
)

package com.devbilal.data.datasource.local.database

import androidx.room.TypeConverter
import com.devbilal.data.datasource.local.database.diaryentry.AttachmentDto
import kotlinx.serialization.json.Json

object ZatTypeConverters {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromAttachmentList(value: List<AttachmentDto>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toAttachmentList(value: String): List<AttachmentDto> {
        return try {
            json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}

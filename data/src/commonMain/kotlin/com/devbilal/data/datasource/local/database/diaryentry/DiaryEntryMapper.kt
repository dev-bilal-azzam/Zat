@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.datasource.local.database.diaryentry

import com.devbilal.domain.entity.Attachment
import com.devbilal.domain.entity.AttachmentType
import com.devbilal.domain.entity.DiaryColor
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryEntrySummary
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun DiaryEntryDto.toEntity(historyCount: Int, attachments: List<Attachment>): DiaryEntry {
    return DiaryEntry(
        id = Uuid.parse(id),
        title = title,
        content = content,
        date = LocalDate.parse(date),
        createdAt = LocalDateTime.parse(createdAt),
        color = DiaryColor(color),
        attachments = attachments,
        historyCount = historyCount
    )
}

fun DiaryEntry.toDto(): DiaryEntryDto {
    return DiaryEntryDto(
        id = id.toString(),
        title = title,
        content = content,
        date = date.toString(),
        createdAt = createdAt.toString(),
        color = color.value
    )
}

fun DiaryEntryDto.toSummary(
    historyCount: Int,
    attachmentTypes: List<AttachmentType> = emptyList()
): DiaryEntrySummary {
    return DiaryEntrySummary(
        id = Uuid.parse(id),
        title = title,
        date = LocalDate.parse(date),
        historyCount = historyCount,
        attachmentTypes = attachmentTypes,
        firstImageUrl = null,
        color = DiaryColor(color)
    )
}

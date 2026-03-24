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

fun DiaryEntryDto.toEntity(historyCount: Int): DiaryEntry {
    return DiaryEntry(
        id = Uuid.parse(id),
        title = title,
        content = content,
        date = LocalDate.parse(date),
        createdAt = LocalDateTime.parse(createdAt),
        color = DiaryColor(color),
        attachments = attachments.map { it.toEntity() },
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
        color = color.value,
        attachments = attachments.map { it.toDto() }
    )
}

fun DiaryEntryDto.toSummary(historyCount: Int): DiaryEntrySummary {
    return DiaryEntrySummary(
        id = Uuid.parse(id),
        title = title,
        date = LocalDate.parse(date),
        historyCount = historyCount,
        attachmentTypes = attachments.map { AttachmentType.valueOf(it.type) }.distinct(),
        firstImageUrl = attachments.filterIsInstance<AttachmentDto.Image>().firstOrNull()?.path
    )
}

fun DiaryEntryWithHistoryCount.toSummary(): DiaryEntrySummary {
    return DiaryEntrySummary(
        id = Uuid.parse(id),
        title = title,
        date = LocalDate.parse(date),
        historyCount = historyCount,
        attachmentTypes = attachments.map { AttachmentType.valueOf(it.type) }.distinct(),
        firstImageUrl = attachments.filterIsInstance<AttachmentDto.Image>().firstOrNull()?.path
    )
}

fun Attachment.toDto(): AttachmentDto = when (this) {
    is Attachment.Image -> AttachmentDto.Image(id = id.toString(), path = path)
    is Attachment.Video -> AttachmentDto.Video(id = id.toString(), path = path)
    is Attachment.Audio -> AttachmentDto.Audio(id = id.toString(), path = path)
}

fun AttachmentDto.toEntity(): Attachment = when (this) {
    is AttachmentDto.Image -> Attachment.Image(id = Uuid.parse(id), path = path)
    is AttachmentDto.Video -> Attachment.Video(id = Uuid.parse(id), path = path)
    is AttachmentDto.Audio -> Attachment.Audio(id = Uuid.parse(id), path = path)
}

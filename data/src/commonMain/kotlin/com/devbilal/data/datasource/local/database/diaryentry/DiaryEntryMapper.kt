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

fun DiaryEntry.toDto(attachments: List<AttachmentDto>): DiaryEntryDto {
    return DiaryEntryDto(
        id = id.toString(),
        title = title,
        content = content,
        date = date.toString(),
        createdAt = createdAt.toString(),
        color = color.value,
        attachments = attachments
    )
}

fun DiaryEntryDto.toSummary(historyCount: Int): DiaryEntrySummary {
    return DiaryEntrySummary(
        id = Uuid.parse(id),
        title = title,
        date = LocalDate.parse(date),
        historyCount = historyCount,
        attachmentTypes = attachments.map(AttachmentDto::toAttachmentType).distinct(),
        firstImageUrl = null,
        color = DiaryColor(color)
    )
}

fun DiaryEntryWithHistoryCount.toSummary(): DiaryEntrySummary {
    return DiaryEntrySummary(
        id = Uuid.parse(id),
        title = title,
        date = LocalDate.parse(date),
        historyCount = historyCount,
        attachmentTypes = attachments.map(AttachmentDto::toAttachmentType).distinct(),
        firstImageUrl = null,
        color = DiaryColor(color)
    )
}

fun Attachment.toDto(path: String): AttachmentDto = when (this) {
    is Attachment.Image -> AttachmentDto.Image(id = id.toString(), filePath = path)
    is Attachment.Video -> AttachmentDto.Video(id = id.toString(), filePath = path)
    is Attachment.Audio -> AttachmentDto.Audio(id = id.toString(), filePath = path)
}

fun AttachmentDto.toEntity(bytes: ByteArray): Attachment = when (this) {
    is AttachmentDto.Image -> Attachment.Image(id = Uuid.parse(id), bytes = bytes)
    is AttachmentDto.Video -> Attachment.Video(id = Uuid.parse(id), bytes = bytes)
    is AttachmentDto.Audio -> Attachment.Audio(id = Uuid.parse(id), bytes = bytes)
}

fun AttachmentDto.toAttachmentType(): AttachmentType = when(this) {
    is AttachmentDto.Audio -> AttachmentType.AUDIO
    is AttachmentDto.Image -> AttachmentType.IMAGE
    is AttachmentDto.Video -> AttachmentType.VIDEO
}
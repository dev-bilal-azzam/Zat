@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.data.datasource.local.database.diaryhistory

import com.devbilal.domain.entity.Attachment
import com.devbilal.domain.entity.DiaryColor
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.entity.DiaryVersion
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun DiaryVersionDto.toEntity(attachments: List<Attachment>): DiaryVersion {
    val entry = DiaryEntry(
        id = Uuid.parse(primaryEntryId),
        title = title,
        content = content,
        date = LocalDate.parse(date),
        createdAt = LocalDateTime.parse(createdAt),
        color = DiaryColor(color),
        attachments = attachments,
        historyCount = 0
    )
    return DiaryVersion(
        id = Uuid.parse(id),
        primaryEntryId = Uuid.parse(primaryEntryId),
        entry = entry,
        versionCreatedAt = LocalDateTime.parse(versionCreatedAt)
    )
}

fun DiaryVersion.toDto(): DiaryVersionDto {
    return DiaryVersionDto(
        id = id.toString(),
        primaryEntryId = primaryEntryId.toString(),
        title = entry.title,
        content = entry.content,
        date = entry.date.toString(),
        createdAt = entry.createdAt.toString(),
        color = entry.color.value,
        versionCreatedAt = versionCreatedAt.toString()
    )
}

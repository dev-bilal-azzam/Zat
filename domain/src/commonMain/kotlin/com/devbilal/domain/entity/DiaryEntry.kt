@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class DiaryEntry(
    val id: Uuid = Uuid.random(),
    val title: String,
    val content: String,
    val date: LocalDate,
    val createdAt: LocalDateTime,
    val color: DiaryColor = DiaryColor.Default,
    val attachments: List<Attachment> = emptyList(),
    val historyCount: Int = 0
)

data class DiaryEntrySummary(
    val id: Uuid,
    val title: String,
    val date: LocalDate,
    val historyCount: Int,
    val attachmentTypes: List<AttachmentType>,
    val firstImageUrl: String?
)

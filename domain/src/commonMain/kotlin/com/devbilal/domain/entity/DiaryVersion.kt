@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.domain.entity

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents a historical version of a diary entry.
 */
data class DiaryVersion(
    val id: Uuid = Uuid.random(),
    val primaryEntryId: Uuid,
    val entry: DiaryEntry,
    val versionCreatedAt: LocalDateTime
)

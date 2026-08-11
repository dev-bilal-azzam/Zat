package com.devbilal.data.datasource.local.database.diaryhistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.devbilal.data.datasource.local.database.attachment.AttachmentDto
import com.devbilal.data.datasource.local.database.attachment.DiaryVersionAttachmentCrossRef
import com.devbilal.data.datasource.local.database.attachment.DeletedDiaryVersionAttachmentCrossRef
import com.devbilal.domain.util.now
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface DiaryHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVersion(version: DiaryVersionDto)

    @Transaction
    @Query("SELECT * FROM diary_versions WHERE id = :versionId")
    suspend fun getVersionWithAttachmentsById(versionId: String): DiaryVersionWithAttachments?

    @Query("SELECT * FROM diary_versions WHERE id = :versionId")
    suspend fun getVersionById(versionId: String): DiaryVersionDto?

    @Query("SELECT * FROM diary_versions WHERE primaryEntryId = :entryId")
    suspend fun getHistoryByEntryIdImmediate(entryId: String): List<DiaryVersionDto>

    @Query("DELETE FROM diary_versions WHERE id = :versionId")
    suspend fun deleteVersion(versionId: String)

    @Query("DELETE FROM diary_versions WHERE primaryEntryId = :entryId")
    suspend fun deleteHistoryByEntryId(entryId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedVersion(version: DeletedDiaryVersionDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedVersions(versions: List<DeletedDiaryVersionDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedVersionCrossRefs(crossRefs: List<DeletedDiaryVersionAttachmentCrossRef>)

    @Query("SELECT attachmentHash FROM diary_version_attachment_cross_ref WHERE versionId = :versionId")
    suspend fun getAttachmentHashesForVersion(versionId: String): List<String>

    @Transaction
    suspend fun softDeleteVersion(versionId: String) {
        val versionWithAttachments = getVersionWithAttachmentsById(versionId) ?: return
        val version = versionWithAttachments.version
        val hashes = getAttachmentHashesForVersion(versionId)
        
        val deletedVersion = DeletedDiaryVersionDto(
            id = version.id,
            primaryEntryId = version.primaryEntryId,
            title = version.title,
            content = version.content,
            date = version.date,
            createdAt = version.createdAt,
            color = version.color,
            versionCreatedAt = version.versionCreatedAt,
            deletedAt = LocalDateTime.now().toString()
        )
        
        insertDeletedVersion(deletedVersion)
        
        if (hashes.isNotEmpty()) {
            val deletedRefs = hashes.map { DeletedDiaryVersionAttachmentCrossRef(versionId, it) }
            insertDeletedVersionCrossRefs(deletedRefs)
        }
        
        deleteVersion(versionId)
    }

    @Transaction
    suspend fun softDeleteHistoryByEntryId(entryId: String) {
        val versions = getHistoryByEntryIdImmediate(entryId)
        if (versions.isEmpty()) return
        
        val now = LocalDateTime.now().toString()
        val deletedVersions = versions.map { version ->
            DeletedDiaryVersionDto(
                id = version.id,
                primaryEntryId = version.primaryEntryId,
                title = version.title,
                content = version.content,
                date = version.date,
                createdAt = version.createdAt,
                color = version.color,
                versionCreatedAt = version.versionCreatedAt,
                deletedAt = now
            )
        }
        
        insertDeletedVersions(deletedVersions)
        
        // Move attachments for each version
        versions.forEach { version ->
            val hashes = getAttachmentHashesForVersion(version.id)
            if (hashes.isNotEmpty()) {
                val deletedRefs = hashes.map { DeletedDiaryVersionAttachmentCrossRef(version.id, it) }
                insertDeletedVersionCrossRefs(deletedRefs)
            }
        }

        deleteHistoryByEntryId(entryId)
    }

    @Transaction
    @Query("SELECT * FROM diary_versions WHERE primaryEntryId = :entryId ORDER BY versionCreatedAt DESC")
    fun getHistoryWithAttachmentsByEntryId(entryId: String): Flow<List<DiaryVersionWithAttachments>>
}

data class DiaryVersionWithAttachments(
    @Embedded val version: DiaryVersionDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "hash",
        associateBy = Junction(
            value = DiaryVersionAttachmentCrossRef::class,
            parentColumn = "versionId",
            entityColumn = "attachmentHash"
        )
    )
    val attachments: List<AttachmentDto>
)


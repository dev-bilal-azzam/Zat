package com.devbilal.data.datasource.local.database.attachment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AttachmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAttachment(attachment: AttachmentDto)

    @Query("SELECT * FROM attachments WHERE hash = :hash")
    suspend fun getAttachmentByHash(hash: String): AttachmentDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntryCrossRef(crossRef: DiaryEntryAttachmentCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVersionCrossRef(crossRef: DiaryVersionAttachmentCrossRef)

    @Query("SELECT * FROM attachments JOIN diary_entry_attachment_cross_ref ON attachments.hash = diary_entry_attachment_cross_ref.attachmentHash WHERE entryId = :entryId")
    suspend fun getAttachmentsForEntry(entryId: String): List<AttachmentDto>

    @Query("SELECT * FROM attachments JOIN diary_version_attachment_cross_ref ON attachments.hash = diary_version_attachment_cross_ref.attachmentHash WHERE versionId = :versionId")
    suspend fun getAttachmentsForVersion(versionId: String): List<AttachmentDto>

    @Query("DELETE FROM diary_entry_attachment_cross_ref WHERE entryId = :entryId")
    suspend fun deleteEntryCrossRefs(entryId: String)

    @Query("DELETE FROM diary_version_attachment_cross_ref WHERE versionId = :versionId")
    suspend fun deleteVersionCrossRefs(versionId: String)

    @Transaction
    suspend fun updateEntryAttachments(entryId: String, attachments: List<AttachmentDto>) {
        deleteEntryCrossRefs(entryId)
        attachments.forEach { attachment ->
            insertAttachment(attachment)
            insertEntryCrossRef(DiaryEntryAttachmentCrossRef(entryId, attachment.hash))
        }
    }

    @Transaction
    suspend fun updateVersionAttachments(versionId: String, attachments: List<AttachmentDto>) {
        deleteVersionCrossRefs(versionId)
        attachments.forEach { attachment ->
            insertAttachment(attachment)
            insertVersionCrossRef(DiaryVersionAttachmentCrossRef(versionId, attachment.hash))
        }
    }

    @Query("""
        SELECT * FROM attachments 
        WHERE hash NOT IN (SELECT attachmentHash FROM diary_entry_attachment_cross_ref)
        AND hash NOT IN (SELECT attachmentHash FROM diary_version_attachment_cross_ref)
        AND hash NOT IN (SELECT thumbnailHash FROM attachments WHERE thumbnailHash IS NOT NULL)
    """)
    suspend fun getOrphanedAttachments(): List<AttachmentDto>

    @Query("DELETE FROM attachments WHERE hash = :hash")
    suspend fun deleteAttachment(hash: String)
}

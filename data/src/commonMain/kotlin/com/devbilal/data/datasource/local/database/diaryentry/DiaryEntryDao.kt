package com.devbilal.data.datasource.local.database.diaryentry

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.devbilal.data.datasource.local.database.attachment.AttachmentDto
import com.devbilal.data.datasource.local.database.attachment.DiaryEntryAttachmentCrossRef
import com.devbilal.data.datasource.local.database.attachment.DeletedDiaryEntryAttachmentCrossRef
import com.devbilal.domain.util.now
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface DiaryEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: DiaryEntryDto)

    @Update
    suspend fun updateEntry(entry: DiaryEntryDto)

    @Transaction
    @Query("SELECT * FROM diary_entries WHERE id = :id")
    suspend fun getEntryWithAttachmentsById(id: String): DiaryEntryWithAttachments?

    @Query("SELECT * FROM diary_entries WHERE id = :id")
    suspend fun getEntryById(id: String): DiaryEntryDto?

    @Query("DELETE FROM diary_entries WHERE id = :id")
    suspend fun deleteEntry(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedEntry(entry: DeletedDiaryEntryDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedEntryCrossRefs(crossRefs: List<DeletedDiaryEntryAttachmentCrossRef>)

    @Query("SELECT attachmentHash FROM diary_entry_attachment_cross_ref WHERE entryId = :entryId")
    suspend fun getAttachmentHashesForEntry(entryId: String): List<String>

    @Transaction
    suspend fun softDeleteEntry(id: String) {
        val entryWithAttachments = getEntryWithAttachmentsById(id) ?: return
        val entry = entryWithAttachments.entry
        val hashes = getAttachmentHashesForEntry(id)
        
        val deletedEntry = DeletedDiaryEntryDto(
            id = entry.id,
            title = entry.title,
            content = entry.content,
            date = entry.date,
            createdAt = entry.createdAt,
            color = entry.color,
            deletedAt = LocalDateTime.now().toString()
        )
        
        insertDeletedEntry(deletedEntry)
        
        if (hashes.isNotEmpty()) {
            val deletedRefs = hashes.map { DeletedDiaryEntryAttachmentCrossRef(id, it) }
            insertDeletedEntryCrossRefs(deletedRefs)
        }

        deleteEntry(id)
    }

    @Transaction
    @Query("""
        SELECT e.*, (SELECT COUNT(*) FROM diary_versions v WHERE v.primaryEntryId = e.id) as historyCount 
        FROM diary_entries e 
        ORDER BY e.date DESC
    """)
    fun getAllEntriesWithAttachmentsAndHistoryCount(): Flow<List<DiaryEntryWithAttachmentsAndHistoryCount>>

    @Query("SELECT COUNT(*) FROM diary_versions WHERE primaryEntryId = :entryId")
    suspend fun getHistoryCount(entryId: String): Int
}

data class DiaryEntryWithAttachments(
    @Embedded val entry: DiaryEntryDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "hash",
        associateBy = Junction(
            value = DiaryEntryAttachmentCrossRef::class,
            parentColumn = "entryId",
            entityColumn = "attachmentHash"
        )
    )
    val attachments: List<AttachmentDto>
)

data class DiaryEntryWithAttachmentsAndHistoryCount(
    @Embedded val entry: DiaryEntryDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "hash",
        associateBy = Junction(
            value = DiaryEntryAttachmentCrossRef::class,
            parentColumn = "entryId",
            entityColumn = "attachmentHash"
        )
    )
    val attachments: List<AttachmentDto>,
    val historyCount: Int
)


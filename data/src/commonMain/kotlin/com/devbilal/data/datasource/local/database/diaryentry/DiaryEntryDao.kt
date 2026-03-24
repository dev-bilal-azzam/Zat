package com.devbilal.data.datasource.local.database.diaryentry

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.devbilal.domain.util.now
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface DiaryEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: DiaryEntryDto)

    @Update
    suspend fun updateEntry(entry: DiaryEntryDto)

    @Query("SELECT * FROM diary_entries WHERE id = :id")
    suspend fun getEntryById(id: String): DiaryEntryDto?

    @Query("DELETE FROM diary_entries WHERE id = :id")
    suspend fun deleteEntry(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedEntry(entry: DeletedDiaryEntryDto)

    @Transaction
    suspend fun softDeleteEntry(id: String) {
        val entry = getEntryById(id) ?: return
        val deletedEntry = DeletedDiaryEntryDto(
            id = entry.id,
            title = entry.title,
            content = entry.content,
            date = entry.date,
            createdAt = entry.createdAt,
            color = entry.color,
            attachments = entry.attachments,
            deletedAt = LocalDateTime.now().toString()
        )
        insertDeletedEntry(deletedEntry)
        deleteEntry(id)
    }

    @Query("""
        SELECT e.*, (SELECT COUNT(*) FROM diary_versions v WHERE v.primaryEntryId = e.id) as historyCount 
        FROM diary_entries e 
        ORDER BY e.date DESC
    """)
    fun getAllEntriesWithHistoryCount(): Flow<List<DiaryEntryWithHistoryCount>>

    @Query("SELECT COUNT(*) FROM diary_versions WHERE primaryEntryId = :entryId")
    suspend fun getHistoryCount(entryId: String): Int
}

data class DiaryEntryWithHistoryCount(
    val id: String,
    val title: String,
    val content: String,
    val date: String,
    val createdAt: String,
    val color: Long,
    val attachments: List<AttachmentDto>,
    val historyCount: Int
)

package com.devbilal.data.datasource.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.devbilal.data.datasource.local.database.attachment.*
import com.devbilal.data.datasource.local.database.diaryentry.DeletedDiaryEntryDto
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDto
import com.devbilal.data.datasource.local.database.diaryhistory.DeletedDiaryVersionDto
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryHistoryDao
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryVersionDto

@Database(
    entities = [
        DiaryEntryDto::class, 
        DiaryVersionDto::class, 
        DeletedDiaryEntryDto::class, 
        DeletedDiaryVersionDto::class,
        AttachmentDto::class,
        DiaryEntryAttachmentCrossRef::class,
        DiaryVersionAttachmentCrossRef::class,
        DeletedDiaryEntryAttachmentCrossRef::class,
        DeletedDiaryVersionAttachmentCrossRef::class
    ],
    version = 4
)
@ConstructedBy(ZatDatabaseConstructor::class)
abstract class ZatDatabase : RoomDatabase() {
    abstract fun getDiaryEntryDao(): DiaryEntryDao
    abstract fun getDiaryHistoryDao(): DiaryHistoryDao
    abstract fun getAttachmentDao(): AttachmentDao
}

@Suppress("KotlinNoActualForExpect")
expect object ZatDatabaseConstructor : RoomDatabaseConstructor<ZatDatabase> {
    override fun initialize(): ZatDatabase
}

const val ZAT_DATABASE_NAME = "zat_database.db"

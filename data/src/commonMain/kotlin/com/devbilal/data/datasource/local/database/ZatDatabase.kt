package com.devbilal.data.datasource.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
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
        DeletedDiaryVersionDto::class
    ],
    version = 1
)
@TypeConverters(ZatTypeConverters::class)
@ConstructedBy(ZatDatabaseConstructor::class)
abstract class ZatDatabase : RoomDatabase() {
    abstract fun getDiaryEntryDao(): DiaryEntryDao
    abstract fun getDiaryHistoryDao(): DiaryHistoryDao
}

@Suppress("KotlinNoActualForExpect")
expect object ZatDatabaseConstructor : RoomDatabaseConstructor<ZatDatabase> {
    override fun initialize(): ZatDatabase
}

const val ZAT_DATABASE_NAME = "zat_database.db"

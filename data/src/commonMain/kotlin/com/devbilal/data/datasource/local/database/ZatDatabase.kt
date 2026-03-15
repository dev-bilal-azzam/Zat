package com.devbilal.data.datasource.local.database


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryHistoryDao

@Database(
    entities = [],
    version = 1
)
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
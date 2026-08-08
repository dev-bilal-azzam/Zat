package com.devbilal.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devbilal.data.datasource.local.database.ZAT_DATABASE_NAME
import com.devbilal.data.datasource.local.database.ZatDatabase
import com.devbilal.data.util.AndroidFileManager
import com.devbilal.data.util.FileManager
import org.koin.core.module.Module
import org.koin.core.scope.Scope

actual fun Scope.getDatabaseBuilder(): RoomDatabase.Builder<ZatDatabase> {
    val context = this.get<Context>()
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(ZAT_DATABASE_NAME)
    return Room.databaseBuilder<ZatDatabase>(context = appContext, name = dbFile.absolutePath)
}

actual fun Module.expectFileManager() {
    single<FileManager> { AndroidFileManager(get()) }
}

@file:OptIn(ExperimentalForeignApi::class)

package com.devbilal.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.devbilal.data.datasource.local.database.ZAT_DATABASE_NAME
import com.devbilal.data.datasource.local.database.ZatDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import com.devbilal.data.util.FileManager
import com.devbilal.data.util.IosFileManager
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun Scope.getDatabaseBuilder(): RoomDatabase.Builder<ZatDatabase> {
    val dbFilePath = documentDirectory() + "/" + ZAT_DATABASE_NAME
    return Room.databaseBuilder<ZatDatabase>(name = dbFilePath)
}

actual fun Module.expectFileManager() {
    single<FileManager> { IosFileManager() }
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
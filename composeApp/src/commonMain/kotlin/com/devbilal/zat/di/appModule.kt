package com.devbilal.zat.di

import com.devbilal.domain.usecase.settings.VersionManager
import com.devbilal.zat.createVersionManager
import com.devbilal.zat.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    single<VersionManager> { createVersionManager() }
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
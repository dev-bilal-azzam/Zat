package com.devbilal.zat.di

import com.devbilal.domain.usecase.settings.VersionManager
import com.devbilal.zat.createVersionManager
import com.devbilal.zat.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    single<VersionManager> { createVersionManager() }
}
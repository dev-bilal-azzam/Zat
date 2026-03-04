package com.devbilal.domain.di

import com.devbilal.domain.service.AppThemeService
import com.devbilal.domain.service.LocalizationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::LocalizationService)
    singleOf(::AppThemeService)
}
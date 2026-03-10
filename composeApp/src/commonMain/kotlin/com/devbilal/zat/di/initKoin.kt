package com.devbilal.zat.di

import com.devbilal.data.di.dataModule
import com.devbilal.domain.di.domainModule
import com.devbilal.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(
            modules = dataModule + domainModule + presentationModule + platformModule()
        )
    }
}
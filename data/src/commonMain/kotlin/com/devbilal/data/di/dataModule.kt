package com.devbilal.data.di

import org.koin.dsl.module

val dataModule = module { includes(repositoryModule, localStorageModule) }
package com.devbilal.zat

import android.app.Application
import com.devbilal.zat.di.initKoin
import org.koin.android.ext.koin.androidContext

class ZatApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@ZatApp)
        }
    }
}
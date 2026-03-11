package com.devbilal.zat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.devbilal.presentation.common.biometric.ActivityProvider
import com.devbilal.zat.main.ZatMain
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val activityProvider by inject<ActivityProvider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen()

        activityProvider.activity = this

        val appLocalizer by inject<AppLocalizer>()
        appLocalizer.applyLocaleToContext()

        setContent {
            ZatMain()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityProvider.activity = null
    }
}
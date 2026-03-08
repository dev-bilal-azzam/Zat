package com.devbilal.zat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val appLocalizer by inject<AppLocalizer>()
        appLocalizer.applyLocaleToContext()

        setContent {
            ZatRoot()
        }
    }
}

@Preview
@Composable
fun ZatRootAndroidPreview() {
    ZatRoot()
}
package com.devbilal.zat

import androidx.compose.ui.window.ComposeUIViewController
import com.devbilal.zat.di.initKoin
import com.devbilal.zat.main.ZatMain

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    ZatMain()
}
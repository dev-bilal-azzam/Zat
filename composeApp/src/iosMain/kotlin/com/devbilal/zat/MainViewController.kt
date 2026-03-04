package com.devbilal.zat

import androidx.compose.ui.window.ComposeUIViewController
import com.devbilal.zat.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}
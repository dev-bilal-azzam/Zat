package com.devbilal.zat

import android.content.Context
import android.os.LocaleList
import androidx.core.os.LocaleListCompat
import com.devbilal.domain.usecase.settings.AppLanguageUseCase
import com.devbilal.domain.util.AppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

actual class AppLocalizer(
    private val context: Context,
    private val languageUseCase: AppLanguageUseCase
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var currentLanguage: String = AppLanguage.DEFAULT.iso

    init {
        coroutineScope.launch {
            settingsLanguageFlow()
        }
    }


    fun applyLocaleToContext(iso: String = currentLanguage) {
        val localeList = LocaleList.forLanguageTags(iso)
        LocaleList.setDefault(localeList)
        val config = context.resources.configuration
        config.setLocales(localeList)
    }

    private suspend fun settingsLanguageFlow() {
        languageUseCase.observeAppLanguage().collect { lang ->
            currentLanguage = lang.iso.ifEmpty {
                val deviceIso = getDeviceLanguageIso()
                languageUseCase.setAppLanguage(AppLanguage.fromIso(deviceIso))
                applyLocaleToContext(deviceIso)
                deviceIso
            }

            applyLocaleToContext(currentLanguage)
        }
    }

    private fun getDeviceLanguageIso(): String {
        val deviceLocale = LocaleListCompat.getDefault()[0]
        return deviceLocale?.language ?: AppLanguage.ENGLISH.iso
    }
}


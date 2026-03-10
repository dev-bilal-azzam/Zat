package com.devbilal.zat

import com.devbilal.domain.usecase.settings.AppLanguageUseCase
import com.devbilal.domain.util.AppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages


actual class AppLocalizer(
    languageUseCase: AppLanguageUseCase
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        coroutineScope.launch {
            languageUseCase.observeAppLanguage().collectLatest { currentLanguage ->
                val iso = currentLanguage.iso.ifEmpty {
                    val deviceIso =
                        NSLocale.preferredLanguages.firstOrNull()?.toString()?.split("-")
                            ?.firstOrNull() ?: AppLanguage.DEFAULT.iso
                    languageUseCase.setAppLanguage(AppLanguage.fromIso(deviceIso))
                    deviceIso
                }
                val defaults = NSUserDefaults.standardUserDefaults
                defaults.setObject(arrayListOf(iso), forKey = "AppleLanguages")
            }
        }
    }
}


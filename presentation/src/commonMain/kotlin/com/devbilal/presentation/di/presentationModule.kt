package com.devbilal.presentation.di

import com.devbilal.presentation.common.biometric.BiometricPromptManager
import com.devbilal.presentation.common.biometric.createBiometricPromptManager
import org.koin.dsl.module


val presentationModule = module {
    single<BiometricPromptManager> { createBiometricPromptManager() }
    includes(viewModelModule)

}
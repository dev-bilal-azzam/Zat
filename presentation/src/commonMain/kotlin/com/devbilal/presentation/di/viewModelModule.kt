package com.devbilal.presentation.di

import com.devbilal.presentation.features.home.HomeViewModel
import com.devbilal.presentation.features.initbiometric.InitBiometricViewModel
import com.devbilal.presentation.features.initsecurity.InitSecurityViewModel
import com.devbilal.presentation.features.onboarding.OnBoardingViewModel
import com.devbilal.presentation.features.setuppattern.SetupPatternViewModel
import com.devbilal.presentation.features.setuppin.SetupPinViewModel
import com.devbilal.presentation.features.unlock.UnlockViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::InitSecurityViewModel)
    viewModelOf(::SetupPatternViewModel)
    viewModelOf(::SetupPinViewModel)
    viewModelOf(::InitBiometricViewModel)
    viewModelOf(::UnlockViewModel)
    viewModelOf(::HomeViewModel)
}
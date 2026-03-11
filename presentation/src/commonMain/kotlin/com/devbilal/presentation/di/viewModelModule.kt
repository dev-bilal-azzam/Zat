package com.devbilal.presentation.di

import com.devbilal.presentation.features.addeditdiary.AddEditDiaryViewModel
import com.devbilal.presentation.features.auth.initbiometric.InitBiometricViewModel
import com.devbilal.presentation.features.auth.initsecurity.InitSecurityViewModel
import com.devbilal.presentation.features.auth.onboarding.OnBoardingViewModel
import com.devbilal.presentation.features.auth.setuppattern.SetupPatternViewModel
import com.devbilal.presentation.features.auth.setuppin.SetupPinViewModel
import com.devbilal.presentation.features.auth.unlock.UnlockViewModel
import com.devbilal.presentation.features.calendar.CalendarViewModel
import com.devbilal.presentation.features.home.HomeViewModel
import com.devbilal.presentation.features.search.SearchViewModel
import com.devbilal.presentation.features.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    // Auth
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::InitSecurityViewModel)
    viewModelOf(::SetupPatternViewModel)
    viewModelOf(::SetupPinViewModel)
    viewModelOf(::InitBiometricViewModel)
    viewModelOf(::UnlockViewModel)

    // App
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::CalendarViewModel)
    viewModelOf(::AddEditDiaryViewModel)

}

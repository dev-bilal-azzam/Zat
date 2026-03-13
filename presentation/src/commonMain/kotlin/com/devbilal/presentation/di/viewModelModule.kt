package com.devbilal.presentation.di

import com.devbilal.presentation.features.auth.screens.initbiometric.InitBiometricViewModel
import com.devbilal.presentation.features.auth.screens.initsecurity.InitSecurityViewModel
import com.devbilal.presentation.features.auth.screens.onboarding.OnBoardingViewModel
import com.devbilal.presentation.features.auth.screens.setuppattern.SetupPatternViewModel
import com.devbilal.presentation.features.auth.screens.setuppin.SetupPinViewModel
import com.devbilal.presentation.features.auth.screens.unlock.UnlockViewModel
import com.devbilal.presentation.features.diary.screens.addeditdiary.AddEditDiaryViewModel
import com.devbilal.presentation.features.diary.screens.calendar.CalendarViewModel
import com.devbilal.presentation.features.diary.screens.home.HomeViewModel
import com.devbilal.presentation.features.diary.screens.search.SearchViewModel
import com.devbilal.presentation.features.diary.screens.settings.SettingsViewModel
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

package com.devbilal.presentation.di

import com.devbilal.presentation.features.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::OnBoardingViewModel)
}
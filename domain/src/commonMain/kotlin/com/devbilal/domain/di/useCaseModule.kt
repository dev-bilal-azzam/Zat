package com.devbilal.domain.di

import com.devbilal.domain.usecase.authentication.AuthenticateWithPrimaryMethodUseCase
import com.devbilal.domain.usecase.authentication.BiometricAuthenticationUseCase
import com.devbilal.domain.usecase.authentication.GetAuthenticationSettingsUseCase
import com.devbilal.domain.usecase.authentication.IsAvailableBiometricAuthentication
import com.devbilal.domain.usecase.authentication.SetAuthenticationMethodUseCase
import com.devbilal.domain.usecase.diary.CleanOrphanedAttachmentsUseCase
import com.devbilal.domain.usecase.diary.ClearTempCacheUseCase
import com.devbilal.domain.usecase.diary.DeleteDiaryEntryUseCase
import com.devbilal.domain.usecase.diary.DeleteDiaryHistoryUseCase
import com.devbilal.domain.usecase.diary.DeleteDiaryVersionUseCase
import com.devbilal.domain.usecase.diary.EditDiaryEntryUseCase
import com.devbilal.domain.usecase.diary.GetAllDiaryEntriesUseCase
import com.devbilal.domain.usecase.diary.GetDiaryEntryUseCase
import com.devbilal.domain.usecase.diary.GetDiaryHistoryUseCase
import com.devbilal.domain.usecase.diary.GetStreakCountUseCase
import com.devbilal.domain.usecase.diary.SaveDiaryEntryUseCase
import com.devbilal.domain.usecase.settings.AppLanguageUseCase
import com.devbilal.domain.usecase.settings.AppThemeUseCase
import com.devbilal.domain.usecase.settings.OnboardingDoneUseCase
import com.devbilal.domain.usecase.settings.VersionUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    // Auth
    singleOf(::OnboardingDoneUseCase)
    singleOf(::GetAuthenticationSettingsUseCase)
    singleOf(::BiometricAuthenticationUseCase)
    singleOf(::AuthenticateWithPrimaryMethodUseCase)
    singleOf(::IsAvailableBiometricAuthentication)
    singleOf(::SetAuthenticationMethodUseCase)

    // Settings
    singleOf(::AppThemeUseCase)
    singleOf(::AppLanguageUseCase)
    singleOf(::VersionUseCase)

    // Diary
    singleOf(::DeleteDiaryEntryUseCase)
    singleOf(::DeleteDiaryHistoryUseCase)
    singleOf(::DeleteDiaryVersionUseCase)
    singleOf(::EditDiaryEntryUseCase)
    singleOf(::GetDiaryEntryUseCase)
    singleOf(::GetAllDiaryEntriesUseCase)
    singleOf(::GetStreakCountUseCase)
    singleOf(::GetDiaryHistoryUseCase)
    singleOf(::SaveDiaryEntryUseCase)
    singleOf(::ClearTempCacheUseCase)
    singleOf(::CleanOrphanedAttachmentsUseCase)
}
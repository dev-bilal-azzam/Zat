package com.devbilal.presentation.features.diary.common.navigation

import com.devbilal.presentation.common.navigation.Route


// Home
internal fun Navigator.navigateToHome() = navigate(Route.Home)

// Settings
internal fun Navigator.navigateToSettings() = navigate(Route.Settings)

// Search
internal fun Navigator.navigateToSearch() = navigate(Route.Search)

// Calendar
internal fun Navigator.navigateToCalendar() = navigate(Route.Calendar)

// AddEditDiary
internal fun Navigator.navigateToAddEditDiary() = navigate(Route.AddEditDiary)

// Security
internal fun Navigator.navigateToSecurity() = navigate(Route.Security)

// Unlock
internal fun Navigator.navigateToUnlock(
    title: String? = null,
    description: String? = null,
    onSuccessfulUnlock: (() -> Unit)? = null
) = navigate(Route.Unlock(title, description, onSuccessfulUnlock))

// Set Pin
internal fun Navigator.navigateToSetPin(onSuccessfulSetup: (() -> Unit)? = null) = navigate(Route.SetupPin(onSuccessfulSetup))

// Set Pattern
internal fun Navigator.navigateToSetPattern(onSuccessfulSetup: (() -> Unit)? = null) = navigate(Route.SetupPattern(onSuccessfulSetup))
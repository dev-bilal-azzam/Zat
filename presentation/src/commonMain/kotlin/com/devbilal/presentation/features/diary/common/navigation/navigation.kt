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

internal fun Navigator.navigateToSecuritySettings() = navigate(Route.Security)

package com.devbilal.designsystem.component.snackbar

import com.devbilal.designsystem.component.uitext.UiText


data class SnackBarHostState (
    val isVisible: Boolean = false,
    val snackBarData: SnackBarData = SnackBarData(
        title = UiText.DynamicString(),
        message = UiText.DynamicString()
    )
)

data class SnackBarData(
    val title: UiText,
    val message: UiText,
    val isError: Boolean = true,
    val duration: Long = 2500
)
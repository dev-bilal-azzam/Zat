package com.devbilal.presentation.features.diary.screens.addeditdiary

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.domain.entity.DiaryColor
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState
import kotlinx.datetime.LocalDate

data class AddEditDiaryState(
    val id: String? = null,
    val title: String = "",
    val content: String = "",
    val date: LocalDate,
    val color: DiaryColor = DiaryColor.Default,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val isDatePickerShown: Boolean = false,
) : UiState

sealed interface AddEditDiaryIntent : UiIntent {
    data object OnBackClicked : AddEditDiaryIntent
    data object OnSaveClicked : AddEditDiaryIntent
    data class OnTitleChanged(val title: String) : AddEditDiaryIntent
    data class OnContentChanged(val content: String) : AddEditDiaryIntent
    data class OnDateChanged(val date: LocalDate) : AddEditDiaryIntent
    data class OnColorChanged(val color: DiaryColor) : AddEditDiaryIntent
    data object OnShowDatePicker : AddEditDiaryIntent
    data object OnDismissDatePicker : AddEditDiaryIntent
}

sealed interface AddEditDiaryEffect : UiEffect {
    data object NavigateBack : AddEditDiaryEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : AddEditDiaryEffect
}

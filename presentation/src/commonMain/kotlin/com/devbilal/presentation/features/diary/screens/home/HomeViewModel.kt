package com.devbilal.presentation.features.diary.screens.home

import androidx.lifecycle.viewModelScope
import com.devbilal.domain.usecase.diary.GetAllDiaryEntriesUseCase
import com.devbilal.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class HomeViewModel(
    private val getAllDiaryEntriesUseCase: GetAllDiaryEntriesUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    HomeState()
) {

    init {
        getAllEntries()
    }

    private fun getAllEntries() {
        getAllDiaryEntriesUseCase()
            .onStart { updateState { copy(isLoading = true) } }
            .onEach { entries ->
                updateState { copy(entries = entries, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnBackClicked -> sendEffect(HomeEffect.NavigateBack)
            HomeIntent.OnAddEntryClicked -> sendEffect(HomeEffect.NavigateToAddEntry)
            is HomeIntent.OnEntryClicked -> sendEffect(HomeEffect.NavigateToEditEntry(intent.id))
        }
    }
}

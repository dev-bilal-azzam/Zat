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
    private val getAllDiaryEntriesUseCase: GetAllDiaryEntriesUseCase,
    private val deleteDiaryEntryUseCase: com.devbilal.domain.usecase.diary.DeleteDiaryEntryUseCase
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
            is HomeIntent.OnSwipeToDelete -> updateState { copy(pendingDeleteEntryId = intent.id) }
            HomeIntent.OnConfirmDelete -> {
                val id = currentState.pendingDeleteEntryId ?: return
                updateState { copy(pendingDeleteEntryId = null) }
                safeExecute { deleteDiaryEntryUseCase(id) }
            }
            HomeIntent.OnCancelDelete -> updateState { copy(pendingDeleteEntryId = null) }
        }
    }
}

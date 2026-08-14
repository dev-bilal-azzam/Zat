package com.devbilal.presentation.features.diary.screens.home

import androidx.lifecycle.viewModelScope
import com.devbilal.domain.usecase.diary.DeleteDiaryEntryUseCase
import com.devbilal.domain.usecase.diary.GetAllDiaryEntriesUseCase
import com.devbilal.domain.usecase.diary.GetStreakCountUseCase
import com.devbilal.domain.util.now
import com.devbilal.domain.util.today
import com.devbilal.presentation.base.BaseViewModel
import com.devbilal.presentation.common.utils.localizedFormat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.greeting_afternoon
import zat.presentation.generated.resources.greeting_evening
import zat.presentation.generated.resources.greeting_morning
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class HomeViewModel(
    private val getAllDiaryEntriesUseCase: GetAllDiaryEntriesUseCase,
    private val deleteDiaryEntryUseCase: DeleteDiaryEntryUseCase,
    private val getStreakCountUseCase: GetStreakCountUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    HomeState()
) {

    init {
        observeEntries()
        observeStreak()
        updateGreetingAndDate()
    }

    private fun updateGreetingAndDate() {
        val now = LocalDateTime.now()
        val hour = now.hour
        val today = LocalDate.today()

        updateState {
            copy(
                greetingRes = getGreetingRes(hour),
                date = today.localizedFormat()
            )
        }
    }

    private fun getGreetingRes(hour: Int): StringResource {
        return when (hour) {
            in 0..11 -> Res.string.greeting_morning
            in 12..16 -> Res.string.greeting_afternoon
            else -> Res.string.greeting_evening
        }
    }

    private fun observeStreak() {
        getStreakCountUseCase()
            .onEach { count -> updateState { copy(streakCount = count) } }
            .launchIn(viewModelScope)
    }

    private fun observeEntries() {
        getAllDiaryEntriesUseCase()
            .onStart { updateState { copy(isLoading = true) } }
            .onEach { entries ->
                val today = LocalDate.today()
                val todayEntry = entries.find { it.date == today }
                val recentEntries = entries.filter { it.date != today }

                updateState {
                    copy(
                        entries = recentEntries,
                        todayEntry = todayEntry,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnEntryClicked -> sendEffect(HomeEffect.NavigateToEditEntry(intent.id))
            HomeIntent.OnAddEntryClicked -> sendEffect(HomeEffect.NavigateToAddEntry)
            is HomeIntent.OnSwipeToDelete -> updateState { copy(pendingDeleteEntryId = intent.id) }
            HomeIntent.OnConfirmDelete -> {
                val id = currentState.pendingDeleteEntryId ?: return
                updateState { copy(pendingDeleteEntryId = null) }
                safeExecute { deleteDiaryEntryUseCase(id) }
            }

            HomeIntent.OnCancelDelete -> updateState { copy(pendingDeleteEntryId = null) }
            HomeIntent.OnSearchClicked -> sendEffect(HomeEffect.NavigateToSearch)
            HomeIntent.OnTrashClicked -> sendEffect(HomeEffect.NavigateToTrash)
            HomeIntent.OnCreateTodayEntryClicked -> sendEffect(HomeEffect.NavigateToAddEntry)
        }
    }
}

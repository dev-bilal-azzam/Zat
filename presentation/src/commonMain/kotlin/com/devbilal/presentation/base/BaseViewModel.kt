package com.devbilal.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Intent : UiIntent, Effect : UiEffect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected val currentState: State get() = _state.value

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    abstract fun handleIntent(intent: Intent)

    protected fun updateState(reduce: State.() -> State) {
        _state.update { it.reduce() }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    protected fun <T> safeExecute(
        onStart: suspend () -> Unit = {},
        onSuccess: suspend (T) -> Unit = {},
        onError: suspend (Throwable) -> Unit = {},
        onCompleted: suspend () -> Unit = {},
        block: suspend () -> T
    ) {
        viewModelScope.launch {
            onStart()
            runCatching { block() }
                .onSuccess { onSuccess(it) }
                .onFailure { onError(it) }
            onCompleted()
        }
    }

    protected fun <T> safeCollect(
        block: suspend () -> Flow<T>,
        onStart: suspend () -> Unit = {},
        onCollect: suspend (T) -> Unit = {},
        onError: suspend (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            onStart()
            block()
                .catch { onError(it) }
                .collect { onCollect(it) }
        }
    }
}


/**
 * Interface for all one-time side effects.
 * Implement this in your feature's Effect sealed interface.
 */
interface UiEffect


/**
 * Interface for all user intents/events.
 * Implement this in your feature's Event sealed interface.
 */
interface UiIntent


/**
 * Interface for all UI states.
 * Implement this in your feature's State data class.
 */
interface UiState

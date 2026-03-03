package com.devbilal.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@Composable
fun <S : UiState, I : UiIntent, E : UiEffect> BaseViewModel<S, I, E>.collectState(): S {
    val state by state.collectAsStateWithLifecycle()
    return state
}


@Composable
fun <S : UiState, I : UiIntent, E : UiEffect> BaseViewModel<S, I, E>.ObserveEffects(
    onEffect: suspend (E) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                effect.collectLatest(onEffect)
            }
        }
    }
}
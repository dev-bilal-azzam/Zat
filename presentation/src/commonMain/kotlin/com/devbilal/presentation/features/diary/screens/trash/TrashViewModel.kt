package com.devbilal.presentation.features.diary.screens.trash

import com.devbilal.presentation.base.*

class TrashViewModel(
) : BaseViewModel<TrashState, TrashIntent, TrashEffect>(TrashState()) {

    init {
    }

    override fun handleIntent(intent: TrashIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}
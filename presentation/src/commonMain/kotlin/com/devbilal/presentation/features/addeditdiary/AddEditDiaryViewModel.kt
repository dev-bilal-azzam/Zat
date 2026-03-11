package com.devbilal.presentation.features.addeditdiary

import com.devbilal.presentation.base.*

class AddEditDiaryViewModel(
) : BaseViewModel<AddEditDiaryState, AddEditDiaryIntent, AddEditDiaryEffect>(AddEditDiaryState()) {

    init {
    }

    override fun handleIntent(intent: AddEditDiaryIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}
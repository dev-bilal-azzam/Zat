package com.devbilal.presentation.features.diary.screens.addeditdiary

import com.devbilal.presentation.base.BaseViewModel

class AddEditDiaryViewModel : BaseViewModel<AddEditDiaryState, AddEditDiaryIntent, AddEditDiaryEffect>(
    AddEditDiaryState()
) {

    init {
    }

    override fun handleIntent(intent: AddEditDiaryIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}
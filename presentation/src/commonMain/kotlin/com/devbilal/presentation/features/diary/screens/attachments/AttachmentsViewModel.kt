package com.devbilal.presentation.features.diary.screens.attachments

import com.devbilal.presentation.base.*

class AttachmentsViewModel(
) : BaseViewModel<AttachmentsState, AttachmentsIntent, AttachmentsEffect>(AttachmentsState()) {

    init {
    }

    override fun handleIntent(intent: AttachmentsIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}
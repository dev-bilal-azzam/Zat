package com.devbilal.presentation.features.search

import com.devbilal.presentation.base.*

class SearchViewModel(
) : BaseViewModel<SearchState, SearchIntent, SearchEffect>(SearchState()) {

    init {
    }

    override fun handleIntent(intent: SearchIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}
package com.devbilal.presentation.features.auth.screens.setuppattern

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.usecase.authentication.SetAuthenticationMethodUseCase
import com.devbilal.presentation.base.BaseViewModel
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.couldnt_set_pattern
import zat.presentation.generated.resources.error

class SetupPatternViewModel(
    private val setAuthenticationMethodUseCase: SetAuthenticationMethodUseCase
) : BaseViewModel<SetupPatternState, SetupPatternIntent, SetupPatternEffect>(
    SetupPatternState()
) {

    override fun handleIntent(intent: SetupPatternIntent) {
        when (intent) {
            is SetupPatternIntent.OnPatternChanged -> onPatternChanged(intent.pattern)
            SetupPatternIntent.OnConfirmClicked -> onConfirmClicked()
            SetupPatternIntent.OnBackClicked -> onBackClicked()
        }
    }

    private fun onPatternChanged(pattern: List<Int>) {
        updateState { copy(pattern = pattern) }
    }

    private fun onConfirmClicked() {
        if (state.value.isConfirmEnabled) {
            safeExecute(
                block = {
                    setAuthenticationMethodUseCase(
                        method = AuthenticationMethod.Pattern(currentState.pattern)
                    )
                },
                onSuccess = { sendEffect(SetupPatternEffect.SuccessfulSetup) },
                onError = { showSnackBar() }
            )
        }
    }

    private fun onBackClicked() {
        sendEffect(SetupPatternEffect.NavigateBack)
    }

    private fun showSnackBar(
        titleStringResource: StringResource = Res.string.error,
        messageStringResource: StringResource = Res.string.couldnt_set_pattern,
        isError: Boolean = false
    ) {
        sendEffect(
            SetupPatternEffect.ShowSnackBar(
                SnackBarData(
                    title = UiText.StringRes(titleStringResource),
                    message = UiText.StringRes(messageStringResource),
                    isError = isError
                )
            )
        )
    }
}

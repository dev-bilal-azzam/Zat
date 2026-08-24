package com.devbilal.designsystem.component.uitext


import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiText{
    data class DynamicString(val value: String = ""): UiText()
    class StringRes(val resId: StringResource, vararg val formatArgs: Any = emptyArray()): UiText()
}
@Composable
fun UiText?.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringRes -> {
            if (formatArgs.isEmpty()) {
                stringResource(resId)
            } else {
                val resolvedArgs = formatArgs.map { arg ->
                    if (arg is UiText) arg.asString() else arg
                }.toTypedArray()

                stringResource(resId, *resolvedArgs)
            }
        }
        else -> ""
    }
}
package com.devbilal.designsystem.component.richtext

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.richtext.RichTextEditor as BaseRichTextEditor

@Composable
fun RichTextEditor(
    state: RichTextState,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    textStyle: TextStyle = Theme.typography.body.medium,
    textColor: Color = Theme.colorScheme.shadePrimary,
    placeholderColor: Color = Theme.colorScheme.shadeTertiary,
    cursorColor: Color = Theme.colorScheme.primary.primary,
) {
    BaseRichTextEditor(
        state = state.delegate,
        placeholder = placeholder,
        placeholderColor = placeholderColor,
        textStyle = textStyle.copy(color = textColor),
        cursorColor = cursorColor,
        modifier = modifier
    )
}

@Composable
@Preview
fun RichTextEditorWithPanelPreview() {
    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }
    val state = rememberRichTextState()

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RichTextPanel(
                state = state
            )
            
            RichTextEditor(
                state = state,
                placeholder = "How was your day? Write it here...",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

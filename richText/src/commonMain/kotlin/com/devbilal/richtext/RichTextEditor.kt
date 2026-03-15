package com.devbilal.richtext

import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

/**
 * A multi-line rich text field that allows formatted text input.
 * Integrated with [RichTextState] to handle formatting.
 *
 * @param state The state of the rich text editor.
 * @param modifier The modifier to be applied to the editor.
 * @param placeholder The placeholder text to be displayed when the editor is empty.
 * @param textStyle The text style to be applied to the editor.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextEditor(
    state: RichTextState,
    placeholder: String,
    placeholderColor: Color,
    textStyle: TextStyle,
    cursorColor: Color,
    modifier: Modifier = Modifier,
) {
    RichTextEditor(
        state = state.internalState,
        modifier = modifier,
        textStyle = textStyle,
        placeholder = {

                Text(
                    text = placeholder,
                    style = textStyle,
                    color = placeholderColor
                )

        },
        colors = RichTextEditorDefaults.richTextEditorColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = cursorColor,
        )
    )
}

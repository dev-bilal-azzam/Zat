package com.devbilal.richtext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.mohamedrejeb.richeditor.model.RichTextState as InternalRichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState as rememberInternalRichTextState


/**
 * A library-agnostic state for rich text editing.
 * Wraps the underlying rich editor library state.
 */
class RichTextState internal constructor(
    internal val internalState: InternalRichTextState
) {
    var isBold: Boolean
        get() = internalState.currentSpanStyle.fontWeight == FontWeight.Bold
        set(value) {
            if (value) internalState.addSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
            else internalState.removeSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
        }

    var isItalic: Boolean
        get() = internalState.currentSpanStyle.fontStyle == FontStyle.Italic
        set(value) {
            if (value) internalState.addSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
            else internalState.removeSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
        }

    var isUnderline: Boolean
        get() = internalState.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true
        set(value) {
            if (value) internalState.addSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
            else internalState.removeSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
        }

    fun toggleBold() = internalState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
    fun toggleItalic() = internalState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
    fun toggleUnderline() = internalState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))

    fun setAlignment(alignment: TextAlign) {
        internalState.toggleParagraphStyle(ParagraphStyle(textAlign = alignment))
    }

    fun toggleUnorderedList() = internalState.toggleUnorderedList()
    fun toggleOrderedList() = internalState.toggleOrderedList()

    fun setTextColor(color: Color) {
        internalState.addSpanStyle(SpanStyle(color = color))
    }

    fun toHtml(): String = internalState.toHtml()
    fun setHtml(html: String) = internalState.setHtml(html)

    val currentAlignment: TextAlign
        get() = internalState.currentParagraphStyle.textAlign ?: TextAlign.Start

    val currentColor: Color
        get() = internalState.currentSpanStyle.color

    val isUnorderedList: Boolean
        get() = internalState.isUnorderedList

    val isOrderedList: Boolean
        get() = internalState.isOrderedList
}

@Composable
fun rememberRichTextState(): RichTextState {
    val internalState = rememberInternalRichTextState()
    return remember(internalState) {
        RichTextState(internalState)
    }
}

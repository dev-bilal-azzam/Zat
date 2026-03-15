package com.devbilal.designsystem.component.richtext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.devbilal.richtext.RichTextState as BaseRichTextState
import com.devbilal.richtext.rememberRichTextState as rememberBaseRichTextState

/**
 * A wrapped state for rich text editing to be used within the design system.
 * This ensures that other modules only interact with the design system components
 * and don't need a direct dependency on the richText module.
 */
class RichTextState internal constructor(
    internal val delegate: BaseRichTextState
) {
    var isBold: Boolean
        get() = delegate.isBold
        set(value) { delegate.isBold = value }

    var isItalic: Boolean
        get() = delegate.isItalic
        set(value) { delegate.isItalic = value }

    var isUnderline: Boolean
        get() = delegate.isUnderline
        set(value) { delegate.isUnderline = value }

    val isUnorderedList: Boolean get() = delegate.isUnorderedList
    val isOrderedList: Boolean get() = delegate.isOrderedList
    val currentAlignment: TextAlign get() = delegate.currentAlignment
    val currentColor: Color get() = delegate.currentColor

    fun toggleBold() = delegate.toggleBold()
    fun toggleItalic() = delegate.toggleItalic()
    fun toggleUnderline() = delegate.toggleUnderline()
    fun toggleUnorderedList() = delegate.toggleUnorderedList()
    fun toggleOrderedList() = delegate.toggleOrderedList()
    fun setAlignment(alignment: TextAlign) = delegate.setAlignment(alignment)
    fun setTextColor(color: Color) = delegate.setTextColor(color)
    
    fun toHtml(): String = delegate.toHtml()
    fun setHtml(html: String) = delegate.setHtml(html)
}

@Composable
fun rememberRichTextState(): RichTextState {
    val baseState = rememberBaseRichTextState()
    return remember(baseState) {
        RichTextState(baseState)
    }
}

package com.devbilal.designsystem.component.button.content

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme

@Composable
internal fun BaseButtonContent(
    text: String?,
    trailingIcon: ImageVector?,
    contentColor: Color,
    iconSize: Dp,
    iconStartPadding: Dp,
    contentDescription: String? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    text?.let {
        Text(
            text = text,
            style = Theme.typography.label.large,
            color = contentColor,
            overflow = overflow,
        )
    }

    trailingIcon?.let {
        Icon(
            imageVector = trailingIcon,
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(start = iconStartPadding)
                .size(iconSize),
            tint = contentColor
        )
    }
}
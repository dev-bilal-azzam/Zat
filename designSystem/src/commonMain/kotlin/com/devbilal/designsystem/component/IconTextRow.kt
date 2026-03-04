package com.devbilal.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun IconTextRow(
    icon: ImageVector,
    text: String,
    contentDescription: String? = null,
    iconTint: Color = Theme.colorScheme.primary.primary,
    textColor: Color = Theme.colorScheme.shadePrimary,
    textStyle: TextStyle = Theme.typography.title.medium,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }

}
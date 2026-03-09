package com.devbilal.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun PinIndicator(
    pin: String,
    modifier: Modifier = Modifier,
    length: Int = 4
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(length) { index ->
            val isFilled = index < pin.length
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = if (isFilled) Theme.colorScheme.primary.primary else Theme.colorScheme.background.surfaceLow,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (isFilled) Theme.colorScheme.primary.primary else Theme.colorScheme.stroke,
                        shape = CircleShape
                    )
            )
        }
    }
}

package com.devbilal.presentation.common.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppTheme
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun PatternView(
    pattern: List<Int>,
    onPatternChanged: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
    dotCount: Int = 3
) {
    var currentTouchPoint by remember { mutableStateOf<Offset?>(null) }
    var dotCenters by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var hitRadius by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val hitDot = dotCenters.indexOfFirst { center ->
                            sqrt((offset.x - center.x).pow(2) + (offset.y - center.y).pow(2)) < hitRadius
                        }
                        if (hitDot == -1) {
                            onPatternChanged(emptyList())
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        onPatternChanged(emptyList())
                        currentTouchPoint = offset
                    },
                    onDrag = { change, _ ->
                        currentTouchPoint = change.position
                    },
                    onDragEnd = {
                        currentTouchPoint = null
                    },
                    onDragCancel = {
                        currentTouchPoint = null
                    }
                )
            }
    ) {
        val dotColor = Theme.colorScheme.primary.primary.copy(alpha = .3f)
        val selectedDotColor = Theme.colorScheme.primary.primary

        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasSize = size
            val cellWidth = canvasSize.width / dotCount
            val cellHeight = canvasSize.height / dotCount
            val dotRadius = 8.dp.toPx()
            hitRadius = cellWidth / 2

            dotCenters = List(dotCount * dotCount) { i ->
                val row = i / dotCount
                val col = i % dotCount
                Offset(
                    x = col * cellWidth + cellWidth / 2,
                    y = row * cellHeight + cellHeight / 2
                )
            }

            currentTouchPoint?.let { touch ->
                dotCenters.forEachIndexed { index, center ->
                    if (!pattern.contains(index)) {
                        val distance = sqrt((touch.x - center.x).pow(2) + (touch.y - center.y).pow(2))
                        if (distance < hitRadius) {
                            onPatternChanged(pattern + index)
                        }
                    }
                }
            }

            // Draw Lines
            if (pattern.isNotEmpty()) {
                for (i in 0 until pattern.size - 1) {
                    drawLine(
                        color = selectedDotColor,
                        start = dotCenters[pattern[i]],
                        end = dotCenters[pattern[i + 1]],
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                currentTouchPoint?.let { touch ->
                    drawLine(
                        color = selectedDotColor,
                        start = dotCenters[pattern.last()],
                        end = touch,
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            // Draw Dots
            dotCenters.forEachIndexed { index, center ->
                val isSelected = pattern.contains(index)
                
                if (isSelected) {
                    drawCircle(
                        color = selectedDotColor.copy(alpha = 0.2f),
                        radius = dotRadius * 2.5f,
                        center = center
                    )
                }
                
                drawCircle(
                    color = if (isSelected) selectedDotColor else dotColor,
                    radius = dotRadius,
                    center = center
                )
            }
        }
    }
}


@Composable
@Preview
fun PreviewPatternView() {
    ZatTheme(appTheme = AppTheme.LIGHT.name) {
        Box(modifier = Modifier.background(Theme.colorScheme.background.surfaceLow).padding(16.dp)) {
            PatternView(pattern = listOf(0, 1, 4, 5, 8), onPatternChanged = {})
        }
    }
}

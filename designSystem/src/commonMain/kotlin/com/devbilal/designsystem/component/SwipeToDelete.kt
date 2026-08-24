package com.devbilal.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
@Composable
fun SwipeToDelete(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.error,
    actionContent: @Composable BoxScope.() -> Unit = {
        Text(
            text = "Delete",
            color = Color.White,
            style = Theme.typography.label.medium
        )
    },
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    var actionWidth by remember { mutableStateOf(0f) }

    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val directionMultiplier = if (isRtl) -1f else 1f

    val maxDrag = -actionWidth

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(backgroundColor)
    ) {
        // Background Delete Action
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .onGloballyPositioned { actionWidth = it.size.width.toFloat() }
                .padding(horizontal = Theme.spacing._24),
            contentAlignment = Alignment.Center
        ) {
            actionContent()
        }

        // Foreground Content
        Box(
            modifier = Modifier
                .offset { IntOffset((offsetX.value).roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            val adjustedDelta = delta * directionMultiplier
                            val newValue = (offsetX.value + adjustedDelta).coerceIn(maxDrag, 0f)
                            offsetX.snapTo(newValue)
                        }
                    },
                    onDragStopped = {
                        scope.launch {
                            if (offsetX.value < maxDrag / 2) {
                                offsetX.animateTo(maxDrag)
                                onDelete()
                                offsetX.animateTo(0f)
                            } else {
                                offsetX.animateTo(0f)
                            }
                        }
                    }
                )
                .background(Theme.colorScheme.background.surface)
        ) {
            content()
        }
    }
}
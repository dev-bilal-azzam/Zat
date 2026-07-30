@file:OptIn(ExperimentalMaterial3Api::class)

package com.devbilal.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme
import sv.lib.squircleshape.SquircleShape

@Composable
fun ZatBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    shape: Shape = SquircleShape(Theme.radius.lg),
    containerColor: Color = Theme.colorScheme.background.surfaceLow,
    contentColor: Color = Theme.colorScheme.shadePrimary,
    tonalElevation: Dp = 0.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.32f),
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor,
        dragHandle = dragHandle,
        content = {
            val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            Column(
                modifier = Modifier.padding(bottom = bottomPadding)
            ) {
                content()
            }
        }
    )
}

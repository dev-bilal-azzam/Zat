package com.devbilal.designsystem.component.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devbilal.designsystem.component.uitext.asString
import com.devbilal.designsystem.theme.theme.Theme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.vectorResource
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.ic_snackbar_error
import zat.designsystem.generated.resources.ic_snackbar_success

@Composable
fun AnimatedSnackBarHost(
    snackBarHostController: SnackBarHostController,
    modifier: Modifier = Modifier,
) {
    val state = snackBarHostController.state.collectAsStateWithLifecycle().value

    val iconId = if (state.snackBarData.isError) Res.drawable.ic_snackbar_error
    else Res.drawable.ic_snackbar_success

    AnimatedVisibility(
        visible = state.isVisible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Theme.spacing._12)
    ) {
        LaunchedEffect(state.snackBarData) {
            delay(state.snackBarData.duration)
            snackBarHostController.dismissSnackBar()
        }
        SnackBar(
            modifier = modifier.clickable(
                onClick = snackBarHostController::dismissSnackBar,
                indication = null,
                interactionSource = null
            ),
            title = state.snackBarData.title.asString(),
            message = state.snackBarData.message.asString(),
            leadingIcon = vectorResource(iconId),
        )
    }
}
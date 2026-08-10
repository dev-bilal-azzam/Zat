package com.devbilal.presentation.features.diary.screens.attachments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.carousel.Carousel
import com.devbilal.designsystem.component.carousel.CarouselIndicator
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.entity.Attachment
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.diary.screens.attachments.components.AudioAttachmentItem
import com.devbilal.presentation.features.diary.screens.attachments.components.ImageAttachmentItem
import com.devbilal.presentation.features.diary.screens.attachments.components.VideoAttachmentItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.attachments

@Composable
fun AttachmentsScreen(
    viewModel: AttachmentsViewModel = koinViewModel()
) {
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            AttachmentsEffect.NavigateBack -> navigator.navigateBack()
        }
    }

    AttachmentsScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun AttachmentsScreenContent(
    state: AttachmentsState,
    onIntent: (AttachmentsIntent) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentIndex,
        pageCount = { state.attachments.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        onIntent(AttachmentsIntent.OnPageChanged(pagerState.currentPage))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = stringResource(Res.string.attachments),
                onLeadingClick = { onIntent(AttachmentsIntent.OnBackClicked) }
            )
        },
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {
        if (state.attachments.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Carousel(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    indicator = null
                ) { index ->
                    when (val attachment = state.attachments[index]) {
                        is Attachment.Image -> {
                            ImageAttachmentItem(filePath = attachment.filePath)
                        }

                        is Attachment.Video -> {
                            VideoAttachmentItem(
                                filePath = attachment.filePath,
                                thumbnail = attachment.thumbnail
                            )
                        }

                        is Attachment.Audio -> {
                            AudioAttachmentItem(
                                filePath = attachment.filePath,
                                state = state.audioPlaybackState,
                                onIntent = onIntent
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Theme.spacing._32),
                    contentAlignment = Alignment.Center
                ) {
                    CarouselIndicator(
                        itemCount = state.attachments.size,
                        currentPage = pagerState.currentPage
                    )
                }
            }
        }
    }
}

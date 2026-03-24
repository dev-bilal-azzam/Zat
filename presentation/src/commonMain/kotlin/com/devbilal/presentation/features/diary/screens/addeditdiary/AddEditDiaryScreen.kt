package com.devbilal.presentation.features.diary.screens.addeditdiary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.datetime.DatePicker
import com.devbilal.designsystem.component.richtext.RichTextEditor
import com.devbilal.designsystem.component.richtext.RichTextPanel
import com.devbilal.designsystem.component.richtext.rememberRichTextState
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryAppBar
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryAttachments
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryCategorize
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.entry_title_hint
import zat.presentation.generated.resources.how_was_your_day_hint

@Composable
fun AddEditDiaryScreen(
    viewModel: AddEditDiaryViewModel = koinViewModel()
) {
    val snackBar = LocalSnackBarHostController.current
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects { effect ->
        when (effect) {
            AddEditDiaryEffect.NavigateBack -> navigator.navigateBack()
            is AddEditDiaryEffect.ShowSnackBar -> {
                snackBar.showSnackBar(effect.snackBarData)
            }
        }
    }

    AddEditDiaryScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun AddEditDiaryScreenContent(
    state: AddEditDiaryState,
    onIntent: (AddEditDiaryIntent) -> Unit
) {
    val density = LocalDensity.current
    var footerHeight by remember { mutableStateOf(0.dp) }
    val panelBottomPadding = (currentKeyboardHeight() - footerHeight - 32.dp).coerceAtLeast(0.dp)

    val richTextState = rememberRichTextState()

    LaunchedEffect(state.content) {
        if (state.content.isNotEmpty() && richTextState.toHtml() != state.content) {
            richTextState.setHtml(state.content)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AddEditDiaryAppBar(
                isEditMode = state.isEditMode,
                onBackClicked = { onIntent(AddEditDiaryIntent.OnBackClicked) },
                onSaveClick = {
                    onIntent(AddEditDiaryIntent.OnContentChanged(richTextState.toHtml()))
                    onIntent(AddEditDiaryIntent.OnSaveClicked)
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AddEditDiaryHeader(
                    date = state.date,
                    onDateClicked = { onIntent(AddEditDiaryIntent.OnPickDateClicked) }
                )

                BasicTextField(
                    value = state.title,
                    onValueChange = { onIntent(AddEditDiaryIntent.OnTitleChanged(it)) },
                    textStyle = Theme.typography.title.large.copy(color = Theme.colorScheme.shadePrimary),
                    cursorBrush = SolidColor(Theme.colorScheme.primary.primary),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (state.title.isEmpty()) {
                            Text(
                                text = stringResource(Res.string.entry_title_hint),
                                style = Theme.typography.title.large,
                                color = Theme.colorScheme.shadeTertiary
                            )
                        }
                        innerTextField()
                    }
                )


                RichTextEditor(
                    state = richTextState,
                    placeholder = stringResource(Res.string.how_was_your_day_hint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                RichTextPanel(
                    state = richTextState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = panelBottomPadding)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            footerHeight = with(density) { coordinates.size.height.toDp() }
                        }
                ) {
                    AddEditDiaryAttachments()

                    AddEditDiaryCategorize(
                        selectedColor = state.color,
                        onColorSelected = { onIntent(AddEditDiaryIntent.OnColorChanged(it)) }
                    )
                }

            }

            if (state.isDatePickerVisible) {
                DatePicker(
                    onDateSelected = { onIntent(AddEditDiaryIntent.OnDateChanged(it)) },
                    onDismissRequest = { onIntent(AddEditDiaryIntent.OnDismissDatePicker) }
                )
            }
        }
    }
}

@Composable
fun currentKeyboardHeight(): Dp {
    val density = LocalDensity.current
    val imeInsets = WindowInsets.ime
    val height = with(density) {
        imeInsets.getBottom(density).toDp()
    }
    return height
}
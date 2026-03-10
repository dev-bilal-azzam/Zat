package com.devbilal.presentation.features.initsecurity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.common.navigation.LocalBackStack
import com.devbilal.presentation.common.navigation.navigateBack
import com.devbilal.presentation.common.navigation.navigateToHomeWithReplaceAll
import com.devbilal.presentation.common.navigation.navigateToSetupPattern
import com.devbilal.presentation.common.navigation.navigateToSetupPin
import com.devbilal.presentation.features.initsecurity.components.InitSecurityHeader
import com.devbilal.presentation.features.initsecurity.components.UnlockMethodsSection
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.security_setup
import zat.presentation.generated.resources.setup_later
import zat.presentation.generated.resources.skip


@Composable
fun InitSecurityScreen(
    viewModel: InitSecurityViewModel = koinViewModel(),
) {
    val backStack = LocalBackStack.current
    viewModel.ObserveEffects {
        when (it) {
            InitSecurityEffect.NavigateBack -> backStack.navigateBack()
            InitSecurityEffect.NavigateToHome -> backStack.navigateToHomeWithReplaceAll()
            InitSecurityEffect.NavigateToSetPattern -> backStack.navigateToSetupPattern()
            InitSecurityEffect.NavigateToSetPin -> backStack.navigateToSetupPin()
        }
    }

    InitSecurityScreenContent(
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun InitSecurityScreenContent(
    onIntent: (InitSecurityIntent) -> Unit = {},
) {
    var footerHeight by remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.security_setup),
                onLeadingClick = { onIntent(InitSecurityIntent.OnBackClicked) }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                InitSecurityHeader()
                UnlockMethodsSection(
                    onPinClicked = { onIntent(InitSecurityIntent.OnPinClicked) },
                    onPatternClicked = { onIntent(InitSecurityIntent.OnPatternClicked) }
                )

                Spacer(modifier = Modifier.height(with(LocalDensity.current) { footerHeight.toDp() }))
            }

            val shadowColor = Theme.colorScheme.shadeSecondary.copy(alpha = .1f)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .dropShadow(RoundedCornerShape(Theme.radius.xl)) {
                        color = shadowColor
                        radius = 16.dp.toPx()
                    }
                    .background(Theme.colorScheme.background.surfaceLow)
                    .padding(24.dp)
                    .align(Alignment.BottomCenter)
                    .onSizeChanged { footerHeight = it.height}
            ) {
                PrimaryButton(
                    text = stringResource(Res.string.skip),
                    onClick = { onIntent(InitSecurityIntent.OnSetupLaterClicked) },
                    modifier = Modifier.fillMaxWidth()
                )


                Text(
                    text = stringResource(Res.string.setup_later),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.shadeTertiary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
@Preview
fun AppDark() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        InitSecurityScreenContent()

    }
}

@Composable
@Preview
fun App() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.LIGHT) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        InitSecurityScreenContent()

    }
}
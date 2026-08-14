package com.devbilal.presentation.features.diary.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.*

@Composable
fun HomeEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Theme.spacing._32),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(Theme.spacing._32 * 6)
                .padding(bottom = Theme.spacing._24),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✨",
                style = Theme.typography.title.large.copy(fontSize = Theme.typography.title.large.fontSize * 3)
            )
        }
        
        Text(
            text = stringResource(Res.string.empty_state_title),
            style = Theme.typography.body.large,
            color = Theme.colorScheme.shadePrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Theme.spacing._8))

        Text(
            text = stringResource(Res.string.empty_state_subtitle),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )
    }
}

package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.color.colorPalette
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.entity.DiaryColor
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.categorize

@Composable
fun AddEditDiaryCategorize(
    selectedColor: DiaryColor,
    onColorSelected: (DiaryColor) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        colorPalette.red.shade400,
        colorPalette.yellow.shade400,
        colorPalette.green.shade400,
        colorPalette.navy.shade400,
        colorPalette.gray.shade500,
        colorPalette.coffee.shade500
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.categorize),
            style = Theme.typography.label.extraSmall,
            color = Theme.colorScheme.shadeTertiary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            colors.forEach { color ->
                val diaryColor = DiaryColor(color.value.toLong())
                ColorItem(
                    color = color.copy(alpha = .5f),
                    isSelected = selectedColor == diaryColor,
                    onClick = { onColorSelected(diaryColor) }
                )
            }
        }
    }
}

@Composable
private fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (isSelected) Modifier.border(2.dp, Theme.colorScheme.shadePrimary, CircleShape)
                else Modifier
            )
            .clickable { onClick() }
    )
}

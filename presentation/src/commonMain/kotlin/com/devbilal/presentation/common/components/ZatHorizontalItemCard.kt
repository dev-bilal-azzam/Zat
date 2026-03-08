package com.devbilal.presentation.common.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_arrow_right_ios
import zat.presentation.generated.resources.ic_pattern

@Composable
fun ZatHorizontalItemCard(
    label: String? = null,
    hint: String? = null,
    leadingIconRes: DrawableResource? = null,
    trailingIconRes: DrawableResource? = null,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.xl),
    containerColor: Color = Theme.colorScheme.background.surface.copy(alpha = .5f),
    borderColor: Color = Theme.colorScheme.brand.brand.copy(alpha = .1f),
    labelColor: Color = Theme.colorScheme.shadePrimary,
    hintColor: Color = Theme.colorScheme.shadeTertiary,
    iconBoxColor: Color = Theme.colorScheme.background.surface,
    iconBoxShape: Shape = RoundedCornerShape(Theme.radius.lg),
    iconTint: Color = Theme.colorScheme.shadeTertiary,
    isEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {

    val alpha by animateFloatAsState(targetValue = if (isEnabled) 1f else 0.5f)

    val clickableModifier = onClick?.let {
        Modifier.clickable(
            enabled = isEnabled,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            role = Role.RadioButton
        ) {
            onClick()
        }
    } ?: Modifier

    Row(
        modifier = modifier
            .background(containerColor, shape)
            .border(2.dp, borderColor.copy(alpha = borderColor.alpha * alpha), shape)
            .padding(16.dp)
            .alpha(alpha)
            .then(clickableModifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start)
        ) {

            leadingIconRes?.let {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconBoxColor, iconBoxShape)
                        .clip(iconBoxShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(leadingIconRes),
                        contentDescription = contentDescription,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }


            Column {
                label?.let { text ->
                    Text(
                        text = text,
                        color = labelColor,
                        style = Theme.typography.headline.small,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                hint?.let { text ->
                    Text(
                        text = text,
                        color = hintColor,
                        style = Theme.typography.label.extraSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        trailingIconRes?.let {
            Icon(
                imageVector = vectorResource(trailingIconRes),
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }


    }
}

@Composable
@Preview
fun PreviewZatHorizontalItemCard() {
    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        Column (
            modifier = Modifier.background(Theme.colorScheme.background.surfaceLow),
            verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
            ZatHorizontalItemCard(
                label = "Pattern",
                hint = "Draw A Shape To Unlock",
                leadingIconRes = Res.drawable.ic_pattern,
                trailingIconRes = Res.drawable.ic_arrow_right_ios,
                modifier = Modifier.fillMaxWidth()
            )

            ZatHorizontalItemCard(
                label = "Pattern",
                hint = "Draw A Shape To Unlock",
                leadingIconRes = Res.drawable.ic_pattern,
                trailingIconRes = Res.drawable.ic_arrow_right_ios,
                modifier = Modifier.fillMaxWidth(),
                isEnabled = false
            )
        }
    }
}
package com.devbilal.presentation.features.diary.screens.security.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_info
import zat.presentation.generated.resources.security_notice
import zat.presentation.generated.resources.security_notice_desc

@Composable
fun SecurityNotice(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.xl))
            .background(Color(0xFFFF9800).copy(alpha = 0.1f))
            .border(1.dp, Color(0xFFFF9800).copy(alpha = 0.2f), RoundedCornerShape(Theme.radius.xl))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.ic_info),
            contentDescription = null,
            tint = Color(0xFFFF9800),
            modifier = Modifier.size(24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(Res.string.security_notice),
                style = Theme.typography.title.small,
                color = Color(0xFFFF9800)
            )

            Text(
                text = stringResource(Res.string.security_notice_desc),
                style = Theme.typography.body.small,
                color = Color(0xFFFF9800).copy(alpha = 0.8f)
            )
        }
    }
}

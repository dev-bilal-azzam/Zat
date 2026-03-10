package com.devbilal.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import org.jetbrains.compose.resources.painterResource
import sv.lib.squircleshape.SquircleShape
import zat.designsystem.generated.resources.Res
import zat.designsystem.generated.resources.ic_cheese_cake

@Composable
fun FabButton(
    painter: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    containerColor: Color = Theme.colorScheme.primary.primary,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    shape: Shape = SquircleShape(Theme.radius.md)
) {
    Button(
        onClick = onClick,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        Icon(
            painter = painter,
            tint = it,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FabButtonPreview() {
    ZatTheme {
        FabButton(
            painter = painterResource(resource = Res.drawable.ic_cheese_cake),
            onClick = {},
            modifier = Modifier
        )
    }
}

package com.devbilal.richtext

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RichTextPanel(
    state: RichTextState,
    colors: List<Color>,
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,
    selectedContentColor: Color,
    selectedContainerColor: Color,
    selectedColorBorderColor: Color,
    chooseColorTitle: String,
    chooseColorTitleStyle: TextStyle,
    chooseColorTitleColor: Color,
    itemTextStyle: TextStyle,
    selectionIcon: @Composable () -> Unit,
    itemSize: Dp = 32.dp,
    colorItemSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
) {
    var showColorPicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Main Toolbar
        Row(
            modifier = Modifier
                .clip(shape)
                .background(backgroundColor)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text Styles
            Row {
                ControlItem(
                    text = "B",
                    isSelected = state.isBold,
                    onClick = { state.toggleBold() },
                    textStyle = itemTextStyle.copy(fontWeight = FontWeight.Bold),
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
                ControlItem(
                    text = "I",
                    isSelected = state.isItalic,
                    onClick = { state.toggleItalic() },
                    textStyle = itemTextStyle.copy(fontFamily = FontFamily.Default, fontStyle = FontStyle.Italic),
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
                ControlItem(
                    text = "U",
                    isSelected = state.isUnderline,
                    onClick = { state.toggleUnderline() },
                    textStyle = itemTextStyle.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
            }

            // Alignment
            Row {
                AlignmentItem(
                    type = AlignmentType.Left,
                    isSelected = state.currentAlignment == TextAlign.Start || state.currentAlignment == TextAlign.Left,
                    onClick = { state.setAlignment(TextAlign.Start) },
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
                AlignmentItem(
                    type = AlignmentType.Center,
                    isSelected = state.currentAlignment == TextAlign.Center,
                    onClick = { state.setAlignment(TextAlign.Center) },
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
                AlignmentItem(
                    type = AlignmentType.Right,
                    isSelected = state.currentAlignment == TextAlign.End || state.currentAlignment == TextAlign.Right,
                    onClick = { state.setAlignment(TextAlign.End) },
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
            }

            // Lists & Color Toggle
            Row {
                ListItem(
                    type = ListType.Unordered,
                    isSelected = state.isUnorderedList,
                    onClick = { state.toggleUnorderedList() },
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
                ListItem(
                    type = ListType.Ordered,
                    isSelected = state.isOrderedList,
                    onClick = { state.toggleOrderedList() },
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
                ControlItem(
                    text = "A",
                    isSelected = showColorPicker,
                    onClick = { showColorPicker = !showColorPicker },
                    textColor = if (state.currentColor == Color.Unspecified) null else state.currentColor,
                    textStyle = itemTextStyle.copy(textDecoration = TextDecoration.Underline),
                    contentColor = contentColor,
                    selectedContentColor = selectedContentColor,
                    selectedContainerColor = selectedContainerColor,
                    itemSize = itemSize
                )
            }
        }

        // Color Picker Row
        if (showColorPicker) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape)
                    .background(backgroundColor)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = chooseColorTitle,
                    style = chooseColorTitleStyle,
                    color = chooseColorTitleColor
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(colors) { color ->
                        ColorItem(
                            color = color,
                            isSelected = state.currentColor == color,
                            onClick = { state.setTextColor(color) },
                            selectedBorderColor = selectedColorBorderColor,
                            selectionIcon = selectionIcon,
                            colorItemSize = colorItemSize
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ControlItem(
    text: String,
    textStyle: TextStyle,
    isSelected: Boolean,
    onClick: () -> Unit,
    textColor: Color? = null,
    selectedContainerColor: Color,
    selectedContentColor: Color,
    contentColor: Color,
    itemSize: Dp

) {
    Box(
        modifier = Modifier
            .size(itemSize)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) selectedContainerColor else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor ?: if (isSelected) selectedContentColor else contentColor
        )
    }
}

@Composable
private fun AlignmentItem(
    type: AlignmentType,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedContainerColor: Color,
    selectedContentColor: Color,
    contentColor: Color,
    itemSize: Dp
) {
    Box(
        modifier = Modifier
            .size(itemSize)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) selectedContainerColor else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val color = if (isSelected) selectedContentColor else contentColor

        Column(
            modifier = Modifier.width(itemSize * .5f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = when (type) {
                AlignmentType.Left -> Alignment.Start
                AlignmentType.Center -> Alignment.CenterHorizontally
                AlignmentType.Right -> Alignment.End
            }
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(color))
            Box(modifier = Modifier.fillMaxWidth(0.7f).height(2.dp).background(color))
            Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(color))
            Box(modifier = Modifier.fillMaxWidth(0.7f).height(2.dp).background(color))
        }
    }
}

@Composable
private fun ListItem(
    type: ListType,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedContainerColor: Color,
    selectedContentColor: Color,
    contentColor: Color,
    itemSize: Dp
) {
    Box(
        modifier = Modifier
            .size(itemSize)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) selectedContainerColor else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val color = if (isSelected) selectedContentColor else contentColor

        Row(
            modifier = Modifier.width(itemSize * .5f),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                repeat(3) {
                    if (type == ListType.Unordered) {
                        Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(color))
                    } else {
                        Text(
                            text = "${it + 1}",
                            fontSize = 5.sp,
                            color = color,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(3) {
                    Box(modifier = Modifier.width(12.dp).height(2.dp).background(color))
                }
            }
        }
    }
}

@Composable
private fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedBorderColor: Color,
    colorItemSize: Dp,
    selectionIcon: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(colorItemSize)
            .clip(CircleShape)
            .background(color)
            .then(
                if (isSelected) Modifier.border(2.dp, selectedBorderColor, CircleShape)
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            selectionIcon()
        }
    }
}

private enum class AlignmentType { Left, Center, Right }
private enum class ListType { Unordered, Ordered }

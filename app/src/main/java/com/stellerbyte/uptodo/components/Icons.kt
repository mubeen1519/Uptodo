package com.stellerbyte.uptodo.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun VectorIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint : Color = LocalContentColor.current,
    contentDescription : String?,
) {
    Icon(imageVector = imageVector, contentDescription = contentDescription, modifier = modifier,tint = tint)
}

@Composable
fun DrawableIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint : Color = LocalContentColor.current,
    contentDescription : String?,

    ) {
    Icon(painter = painter, contentDescription = contentDescription, modifier = modifier,tint = tint)
}

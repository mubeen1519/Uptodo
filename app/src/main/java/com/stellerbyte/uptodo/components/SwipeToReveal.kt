package com.stellerbyte.uptodo.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToReveal(
    modifier: Modifier = Modifier,
    onBackgroundEndClick: () -> Unit,
    direction: Set<RevealDirection>,
    hiddenContentEnd : @Composable (RowScope.() -> Unit),
    onContentClick : () -> Unit,
    backgroundEndColor : Color,
    content : @Composable (Shape) -> Unit

) {
    RevealSwipe(
        modifier = modifier,
        onBackgroundEndClick = onBackgroundEndClick,
        directions = direction,
        maxRevealDp = 25.dp,
        hiddenContentEnd = hiddenContentEnd,
        backgroundCardEndColor = backgroundEndColor,
        onContentClick = onContentClick
    ) {
        content(it)
    }

}

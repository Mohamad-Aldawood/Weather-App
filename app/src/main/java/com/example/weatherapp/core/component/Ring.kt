package com.example.weatherapp.core.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Ring(
    modifier: Modifier = Modifier,
    ringColor: Color = Color.White,
    ringWidth: Dp = 10.dp,
    backgroundColor: Color = Color.LightGray
) {
    Canvas(modifier = modifier) {
        val strokeWidth = ringWidth.toPx()
        val radius = size.minDimension / 2 - strokeWidth / 2
        val center = Offset(size.width / 2, size.height / 2)

        drawCircle(
            color = backgroundColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        drawCircle(
            color = ringColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )
    }
}
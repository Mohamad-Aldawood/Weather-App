package com.example.weatherapp.core.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.weatherapp.ui.theme.Typography

@Composable
fun RingWithText(
    text: String,
    modifier: Modifier = Modifier, /*horizontal: Arrangement.Horizontal= Arrangement.Center,*/
    fontSize: TextUnit,
    ringWidth: Dp,
    ringSize: Dp
) {

    Row(
        modifier = modifier
            .wrapContentSize(),
        /* horizontalArrangement = horizontal*/
    ) {

        Text(
            text = text,
            style = Typography.bodyLarge.copy(
                color = Color.White,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Ring(
            ringWidth = ringWidth,
            modifier = Modifier
                .size(ringSize)
//                    .offset(x=5.dp,y=5.dp)
        )


    }
}
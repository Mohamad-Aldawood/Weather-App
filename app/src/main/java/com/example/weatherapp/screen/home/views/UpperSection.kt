package com.example.weatherapp.screen.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.component.RingWithText
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.ui.theme.Typography

@Composable
fun ColumnScope.UpperSection(currentWeatherInfo: CurrentWeatherInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(0.5f)
    ) {
        currentWeatherInfo.let {
            it.backgroundImage?.let { id ->
                Image(
                    painter = painterResource(id),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize(),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it.todayTempStatus
                    ?.getOrNull(1)
                    ?.let { item ->
                        RingWithText(
                            text = item.value,
                            fontSize = 50.sp,
                            ringWidth = 2.dp,
                            ringSize = 20.dp
                        )
                    }

                it.weatherTitle?.let { text ->
                    Text(
                        text = text.uppercase(),
                        style = Typography.bodyLarge.copy(color = Color.White, fontSize = 40.sp)
                    )
                }
            }

        }
    }

}

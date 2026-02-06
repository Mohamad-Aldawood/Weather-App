package com.example.weatherapp.screen.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.component.RingWithText
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.WeatherItem
import com.example.weatherapp.ui.theme.Typography
import com.example.weatherapp.util.toAlignment

@Composable
fun ColumnScope.LowerSection(currentWeatherInfo: CurrentWeatherInfo, list: List<WeatherItem>) {
    val horizontalPadding = 10.dp
    currentWeatherInfo.backgroundColor?.let { it ->
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize()
                .offset(y = (-3).dp)
                .background(it)
                .verticalScroll(rememberScrollState())
        ) {
            TableHeader(horizontalPadding, currentWeatherInfo)

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                thickness = 1.dp,
                color = Color.White
            )

            TableBody(list, horizontalPadding)
        }
    }
}

@Composable
fun TableHeader(horizontalPadding: Dp, currentWeatherInfo: CurrentWeatherInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        currentWeatherInfo.todayTempStatus?.forEach {
            Column(
                modifier = Modifier.weight(it.weight),
                horizontalAlignment = it.columnAlignment.toAlignment(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                RingWithText(text = it.value, fontSize = 15.sp, ringWidth = 1.dp, ringSize = 5.dp)
                Text(
                    it.title,
                    style = Typography.titleSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    ),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun TableBody(list: List<WeatherItem>, horizontalPadding: Dp) {
    list.forEach {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = it.dayText,
                style = Typography.displayMedium.copy(
                    fontSize = 15.sp,
                    color = Color.White,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.weight(0.4f),
                textAlign = TextAlign.Start
            )
            Icon(
                painter = painterResource(it.icon),
                modifier = Modifier
                    .weight(0.2f)
                    .size(30.dp),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Row(modifier = Modifier.weight(0.4f), horizontalArrangement = Arrangement.End) {
                RingWithText(text = it.temp, fontSize = 15.sp, ringWidth = 1.dp, ringSize = 5.dp)
            }


        }
    }

}
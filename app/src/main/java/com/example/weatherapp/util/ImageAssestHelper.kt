package com.example.weatherapp.util

import com.example.weatherapp.R
import com.example.weatherapp.core.WeatherEnum

object ImageAssestHelper {
    private val weatherIconMap: Map<Int, Int> = (
            listOf(
                200,
                201,
                202,
                210,
                211,
                212,
                221,
                230,
                231,
                232
            ).associateWith { R.drawable.d11 } +
                    listOf(
                        300,
                        301,
                        302,
                        310,
                        311,
                        312,
                        313,
                        314,
                        321
                    ).associateWith { R.drawable.d09 } +
                    listOf(500, 501, 502, 503, 504).associateWith { R.drawable.d10 } +
                    listOf(511).associateWith { R.drawable.d13 } +
                    listOf(520, 521, 522, 531).associateWith { R.drawable.d09 } +
                    listOf(
                        600,
                        601,
                        602,
                        611,
                        612,
                        613,
                        615,
                        616,
                        620,
                        621,
                        622
                    ).associateWith { R.drawable.d13 } +
                    listOf(
                        701,
                        711,
                        721,
                        731,
                        741,
                        751,
                        761,
                        762,
                        771,
                        781
                    ).associateWith { R.drawable.d50 } +
                    listOf(800).associateWith { R.drawable.d01 } +
                    listOf(801).associateWith { R.drawable.d02 } +
                    listOf(802).associateWith { R.drawable.d03 } +
                    listOf(803, 804).associateWith { R.drawable.d04 }
            )

    //    Group 5xx: Rain
    //Group 800: sunny or clear
    //Group 80x: Clouds
    private val backgroundImage: Map<Int, WeatherEnum> = (
            listOf(800).associateWith { WeatherEnum.Sunny } +
                    listOf(801, 802, 803, 804).associateWith { WeatherEnum.Cloudy } +
                    listOf(
                        500,
                        501,
                        511,
                        502,
                        503,
                        504,
                        520,
                        521,
                        522,
                        531
                    ).associateWith { WeatherEnum.Rain })

    fun getIcon(id: Int?): Int {
        return weatherIconMap[id] ?: 0
    }

    fun getWeatherEnum(id: Int): WeatherEnum? = backgroundImage[id]
}
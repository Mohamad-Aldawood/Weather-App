package com.example.weatherapp.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.weatherapp.R

@Stable
fun fontPoppins() = FontFamily(
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_semibold, weight = FontWeight.SemiBold)
)
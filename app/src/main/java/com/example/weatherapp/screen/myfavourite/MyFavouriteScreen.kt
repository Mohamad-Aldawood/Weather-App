package com.example.weatherapp.screen.myfavourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.ui.theme.Typography

@Composable
fun MyFavouriteScreen(
    viewModel: MyFavouriteViewModel = hiltViewModel(), padding: PaddingValues,
) {
    val uiState by viewModel.favourites.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(padding)// softer background than black 30%
    ) {
        items(items = uiState, key = { it.id }) { location ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = location.cityName,
                            style = Typography.titleMedium
                        )
                        location.country?.let {
                            Text(
                                text = it,
                                style = Typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                    Text(
                        text = "${location.lat}, ${location.lon}",
                        style = Typography.bodyMedium
                    )
                }
            }
        }
    }

}
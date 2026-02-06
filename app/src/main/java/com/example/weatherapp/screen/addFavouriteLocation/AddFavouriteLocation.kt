package com.example.weatherapp.screen.addFavouriteLocation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.EventsEffect

@Composable
fun AddFavouriteLocation(
    paddingValues: PaddingValues,
    viewModel: AddFavouriteLocationViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    EventsEffect(viewModel) {
        when (it) {
            is AddFavouriteLocationEvents.DisplayError -> {
                showSnackBar.invoke(it.message.toString())
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        OutlinedTextField(
            value = uiState.query,
            onValueChange = { viewModel.trySendAction(AddFavouriteLocationActions.UpdateQuery(it)) },
            label = { Text(stringResource(R.string.city_name)) },
            placeholder = { Text(stringResource(R.string.e_g_London)) },
            singleLine = true
        )

        Button(
            onClick = {
                viewModel.trySendAction(AddFavouriteLocationActions.SaveAndSearch)
                // Close the keyboard
                keyboardController?.hide()
            },
            enabled = uiState.query.isNotBlank()
        ) {
            Text(stringResource(R.string.save_location))
        }


    }

}

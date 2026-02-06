package com.example.weatherapp.screen.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.core.EventsEffect
import com.example.weatherapp.core.component.LoadingOverlay
import com.example.weatherapp.screen.home.views.LowerSection
import com.example.weatherapp.screen.home.views.UpperSection
import com.example.weatherapp.util.PermissionManager.checkPermissions

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    padding: PaddingValues,
    showSnackBar: (msg: String) -> Unit
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.INTERNET,
    )

    val permissionsGranted = remember { mutableStateOf(false) }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            permissionsGranted.value = result.all { it.value }
            if (permissionsGranted.value) {
                viewModel.trySendAction(
                    HomeAction.GetWeatherInfo
                )
            }
        }
    )

    // Check if permissions are granted on first launch and update state
    LaunchedEffect(Unit) {
        permissionsGranted.value = checkPermissions(context, permissions)
        if (!permissionsGranted.value) {
            permissionsLauncher.launch(permissions.toTypedArray())
        } else
            viewModel.trySendAction(HomeAction.GetWeatherInfo)
    }

    EventsEffect(viewModel) {
        when (it) {
            is HomeEvents.DisplayErrorMsg -> showSnackBar(it.error)
        }
    }

    LoadingOverlay(isLoading = uiState.showLoader)
    if (!uiState.showLoader) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            UpperSection(uiState.currentWeatherInfo)
            LowerSection(uiState.currentWeatherInfo, uiState.list)
        }
    }
}

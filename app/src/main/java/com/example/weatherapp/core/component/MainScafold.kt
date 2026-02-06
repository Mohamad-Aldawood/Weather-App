package com.example.weatherapp.core.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import kotlinx.coroutines.launch

@Composable
fun MainScafold(
    onAddLocationClick: () -> Unit = {},
    oncFavouriteClick: () -> Unit = {},
    onTitleClick: () -> Unit = {},
    content: @Composable (PaddingValues, showSnackBar: (String) -> Unit) -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val showSnackbar: (String) -> Unit = { msg ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(msg)
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(230.dp) // Set a specific width
                    .fillMaxHeight()
            ) {
                Text(
                    "Weather App",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onTitleClick.invoke() })
                HorizontalDivider()
                NavigationDrawerItem(
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add),
                                modifier = Modifier.size(20.dp),
                                tint = Color.Unspecified,
                                contentDescription = "Add"
                            )
                            Text(text = "Add Location")
                        }

                    },
                    selected = false,
                    onClick = { onAddLocationClick.invoke() }
                )
                NavigationDrawerItem(
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_star),
                                modifier = Modifier.size(20.dp),
                                tint = Color.Unspecified,
                                contentDescription = "Favourite"
                            )
                            Text(text = "Favourite")
                        }

                    },
                    selected = false,
                    onClick = { oncFavouriteClick.invoke() }
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    modifier = Modifier.padding(bottom = 50.dp),
                    hostState = snackbarHostState
                )
            },
            topBar = {
                CustomTopBar(drawerState)
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content(innerPadding, showSnackbar)
                }
            },
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
        )
    }
}
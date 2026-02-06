package com.example.weatherapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.component.MainScafold
import com.example.weatherapp.screen.addFavouriteLocation.AddFavouriteLocation
import com.example.weatherapp.screen.home.HomeScreen
import com.example.weatherapp.screen.myfavourite.MyFavouriteScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun InitNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = NavDestination.HOME_SCREEN
) {
    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(NavDestination.HOME_SCREEN) {
            MainScafold(
                oncFavouriteClick = { navController.navigateToMyFavorite() },
                onTitleClick = { navController.navigateToHome() },
                onAddLocationClick = { navController.navigateToAddLocation() }) { paddingValues, showSnackBar ->
                HomeScreen(padding = paddingValues, showSnackBar = { showSnackBar.invoke(it) })
            }
        }
        composable(NavDestination.ADD_FAVOURITE_LOCATION_SCREEN) {
            MainScafold(
                oncFavouriteClick = { navController.navigateToMyFavorite() },
                onTitleClick = { navController.navigateToHome() },
                onAddLocationClick = { navController.navigateToAddLocation() }) { padding, showSnackBar ->
                AddFavouriteLocation(padding, showSnackBar = { showSnackBar.invoke(it) })
            }
        }
        composable(NavDestination.MY_FAVOURITE_SCREEN) {
            MainScafold(
                oncFavouriteClick = { navController.navigateToMyFavorite() },
                onTitleClick = { navController.navigateToHome() },
                onAddLocationClick = { navController.navigateToAddLocation() }) { paddingValues, _ ->
                MyFavouriteScreen(padding = paddingValues)
            }
        }

    }

}


package com.example.weatherapp.core.navigation

import androidx.navigation.NavController

fun NavController.safeNavigate(
    destination: String
) {

    fun extractBeforeQuestionMark(input: String?): String {
        return input?.substringBefore("?") ?: ""
    }

    // Check if the current route is not the same as the destination
    if (extractBeforeQuestionMark(currentBackStackEntry?.destination?.route) != extractBeforeQuestionMark(
            destination
        )
    ) {
        navigate(destination)
    }
}

fun NavController.navigateToAddLocation(): Unit =
    safeNavigate(NavDestination.ADD_FAVOURITE_LOCATION_SCREEN)

fun NavController.navigateToMyFavorite(): Unit = safeNavigate(NavDestination.MY_FAVOURITE_SCREEN)

fun NavController.navigateToHome(): Unit = safeNavigate(NavDestination.HOME_SCREEN)

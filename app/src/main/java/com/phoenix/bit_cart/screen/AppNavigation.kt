package com.phoenix.bit_cart.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phoenix.bit_cart.screen.home.HomeRoute
import com.phoenix.bit_cart.screen.login.LoginRoute
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<NavDestination.Home> {
            HomeRoute()
        }
        composable<NavDestination.Login> {
            LoginRoute()
        }
    }
}

sealed interface NavDestination {
    @Serializable
    object Home
    @Serializable
    object Login
}
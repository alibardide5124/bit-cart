package com.phoenix.bit_cart.screen

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import com.phoenix.bit_cart.data.model.Product
import com.phoenix.bit_cart.screen.about.AboutScreen
import com.phoenix.bit_cart.screen.cart.CartRoute
import com.phoenix.bit_cart.screen.details.DetailsRoute
import com.phoenix.bit_cart.screen.home.HomeRoute
import com.phoenix.bit_cart.screen.login.LoginRoute
import com.phoenix.bit_cart.screen.order.OrderRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<NavDestination.Home> {
            HomeRoute(
                navigateToAbout = { navController.navigate(NavDestination.About) },
                navigateToCart = { navController.navigate(NavDestination.Cart)  },
                navigateToOrder = { navController.navigate(NavDestination.Orders)},
                navigateToLogin = { navController.navigate(NavDestination.Login)},
                navigateToDetails = { navController.navigate(NavDestination.Details(it)) }
            )
        }
        composable<NavDestination.Login> {
            LoginRoute(
                navigateAfterLogin = {
                    navController.navigate(NavDestination.Home) {
                        popUpTo(NavDestination.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<NavDestination.Details>(
            typeMap = mapOf(
                typeOf<Product>() to CustomNavType.ProductType
            )
        ) { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<NavDestination.Details>()
            DetailsRoute(product = args.product, navigateBack = { navController.popBackStack() })
        }
        composable<NavDestination.About> {
            AboutScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable<NavDestination.Cart> {
            CartRoute(
                navigateToHome = { navController.popBackStack() }
            )
        }
        composable<NavDestination.Orders> {
            OrderRoute(
                navigateToHome = { navController.popBackStack() }
            )
        }
    }
}

sealed interface NavDestination {
    @Serializable
    object Home: NavDestination
    @Serializable
    object Login: NavDestination
    @Serializable
    data class Details(val product: Product): NavDestination
    @Serializable
    data object About: NavDestination
    @Serializable
    data object Cart: NavDestination
    @Serializable
    data object Orders: NavDestination
}

object CustomNavType {

    val ProductType = object : NavType<Product>(isNullableAllowed = false) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: Product
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(
            bundle: SavedState,
            key: String
        ): Product? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun serializeAsValue(value: Product): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun parseValue(value: String): Product {
            return Json.decodeFromString(Uri.decode(value))
        }

    }

}
package com.phoenix.bit_cart.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phoenix.bit_cart.data.model.Product

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetails: (Product) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val products by homeViewModel.products.collectAsStateWithLifecycle()

    HomeScreen(
        isLoading = uiState.isLoading,
        products = products,
        onTryAgain = { homeViewModel.onEvent(HomeUiEvent.Refresh) },
        onClickProduct = { navigateToDetails(it) }
    )
}
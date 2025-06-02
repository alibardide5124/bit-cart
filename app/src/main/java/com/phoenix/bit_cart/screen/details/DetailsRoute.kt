package com.phoenix.bit_cart.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phoenix.bit_cart.data.model.Product

@Composable
fun DetailsRoute(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    product: Product
) {
    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(product = product)
}
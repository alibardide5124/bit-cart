package com.phoenix.bit_cart.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsRoute(
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(product = uiState.product)
}
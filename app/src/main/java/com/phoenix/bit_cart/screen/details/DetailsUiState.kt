package com.phoenix.bit_cart.screen.details

data class DetailsUiState(
    val isLoading: Boolean = false,
    val isCartLoading: Boolean = false,
    val errorCart: Boolean = false,
    val quantity: Int = 0,
    val scaleImage: Boolean = false,
    val productId: String = ""
)
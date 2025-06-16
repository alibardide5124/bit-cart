package com.phoenix.bit_cart.screen.cart

data class CartUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val orderPlaced: Boolean = false,
    val name: String = "",
    val address: String = ""
)
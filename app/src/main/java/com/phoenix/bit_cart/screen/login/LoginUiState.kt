package com.phoenix.bit_cart.screen.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
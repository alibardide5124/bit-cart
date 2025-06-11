package com.phoenix.bit_cart.screen.login

data class LoginUiState(
    val username: String = "",
    val usernameError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)
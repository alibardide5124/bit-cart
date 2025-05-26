package com.phoenix.bit_cart.screen.login

sealed interface LoginUiEvent {
    data class OnUsernameChange(val username: String): LoginUiEvent
    data class OnPasswordChange(val password: String): LoginUiEvent
    data object Login: LoginUiEvent
    data object Register: LoginUiEvent
    data object GoogleLogin: LoginUiEvent
}
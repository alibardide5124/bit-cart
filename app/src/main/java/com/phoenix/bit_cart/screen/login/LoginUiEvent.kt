package com.phoenix.bit_cart.screen.login

import android.content.Context

sealed interface LoginUiEvent {
    data class OnUsernameChange(val username: String): LoginUiEvent
    data class OnPasswordChange(val password: String): LoginUiEvent
    data object Login: LoginUiEvent
    data object Register: LoginUiEvent
    data class GoogleLogin(val context: Context): LoginUiEvent
}
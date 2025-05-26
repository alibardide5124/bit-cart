package com.phoenix.bit_cart.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        username = uiState.username,
        onUsernameChange = { loginViewModel.onEvent(LoginUiEvent.OnUsernameChange(it)) },
        password = uiState.password,
        onPasswordChange = { loginViewModel.onEvent(LoginUiEvent.OnPasswordChange(it)) },
        onClickLogin = { loginViewModel.onEvent(LoginUiEvent.Login) },
        onClickRegister = { loginViewModel.onEvent(LoginUiEvent.Register)},
        onClickGoogleLogin = { loginViewModel.onEvent(LoginUiEvent.GoogleLogin) }
    )
}
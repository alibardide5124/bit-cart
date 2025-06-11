package com.phoenix.bit_cart.screen.login

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateAfterLogin: () -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) navigateAfterLogin()
    }

    LoginScreen(
        username = uiState.username,
        onUsernameChange = { loginViewModel.onEvent(LoginUiEvent.OnUsernameChange(it)) },
        usernameError = uiState.usernameError,
        password = uiState.password,
        onPasswordChange = { loginViewModel.onEvent(LoginUiEvent.OnPasswordChange(it)) },
        passwordError = uiState.passwordError,
        onClickLogin = { loginViewModel.onEvent(LoginUiEvent.Login) },
        onClickRegister = { loginViewModel.onEvent(LoginUiEvent.Register)},
        onClickGoogleLogin = { loginViewModel.onEvent(LoginUiEvent.GoogleLogin(activity!!)) }
    )

    if (uiState.isLoading)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
}
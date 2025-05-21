package com.phoenix.bit_cart.screen.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when(event) {
            is LoginUiEvent.OnUsernameChange ->
                _uiState.update { it.copy(username = event.username) }
            is LoginUiEvent.OnPasswordChange ->
                _uiState.update { it.copy(password = event.password) }
            LoginUiEvent.OnPasswordLogin -> Unit
            LoginUiEvent.OnGoogleLogin -> Unit
        }
    }

}
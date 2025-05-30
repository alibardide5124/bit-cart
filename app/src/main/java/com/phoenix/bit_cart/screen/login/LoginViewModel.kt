package com.phoenix.bit_cart.screen.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.bit_cart.data.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val authManager: AuthManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when(event) {
            is LoginUiEvent.OnUsernameChange -> {
                _uiState.update { it.copy(username = event.username) }
                if (_uiState.value.usernameError)
                    _uiState.update { it.copy(usernameError = !event.username.isValidEmail()) }
            }

            is LoginUiEvent.OnPasswordChange -> {
                _uiState.update { it.copy(password = event.password) }
                if (_uiState.value.passwordError)
                    _uiState.update { it.copy(usernameError = !event.password.isValidEmail()) }
            }

            LoginUiEvent.Login ->
                loginWithPassword()

            LoginUiEvent.Register ->
                registerWithPassword()

            LoginUiEvent.GoogleLogin ->
                loginWithGoogle()
        }
    }

    private fun loginWithPassword() {
        val username = _uiState.value.username
        val password = _uiState.value.password
        if (!areUsernamePasswordValid(username, password)) return

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authManager.signInWithEmail(username, password)
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun registerWithPassword() {
        val username = _uiState.value.username
        val password = _uiState.value.password
        if (!areUsernamePasswordValid(username, password)) return

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authManager.signUpWithEmail(username, password)
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun loginWithGoogle() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authManager.loginGoogleUser()
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun areUsernamePasswordValid(username: String, password: String): Boolean {
        val isPasswordValid = password.isValidPassword()
        val isUsernameValid = username.isValidEmail()

        if (!isUsernameValid) _uiState.update { it.copy(usernameError = true) }
        if (!isPasswordValid) _uiState.update { it.copy(passwordError = true) }

        return isPasswordValid && isUsernameValid
    }

    private fun String.isValidEmail(): Boolean {
        return this.matches(Patterns.EMAIL_ADDRESS.toRegex())
    }

    private fun String.isValidPassword(): Boolean {
        if (this.length < 8) return false
        val hasLetter = this.any { it.isLetter() }
        val hasDigits = this.any { it.isDigit() }
        return hasLetter && hasDigits
    }

}
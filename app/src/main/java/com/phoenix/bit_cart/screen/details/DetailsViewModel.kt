package com.phoenix.bit_cart.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.bit_cart.data.CartManager
import com.phoenix.bit_cart.data.IntResponse
import com.phoenix.bit_cart.data.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val cartManager: CartManager
): ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            DetailsUiEvent.AddToCart ->
                addToCart()

            DetailsUiEvent.RemoveFromCart ->
                removeFromCart()

            DetailsUiEvent.OnClickImage ->
                _uiState.update { it.copy(scaleImage = true) }

            DetailsUiEvent.OnCloseImage ->
                _uiState.update { it.copy(scaleImage = false) }
        }
    }

    fun init(productId: String) {
        _uiState.update { it.copy(isCartLoading = true, productId = productId) }
        viewModelScope.launch {
            val result = cartManager.getProductQuantityInCart(productId)
            if (result is IntResponse.Success) {
                _uiState.update { it.copy(errorCart = false, quantity = result.result) }
            } else {
                _uiState.update { it.copy(errorCart = true) }
            }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isCartLoading = false) }
        }
    }

    private fun addToCart() {
        _uiState.update { it.copy(isCartLoading = true) }
        viewModelScope.launch {
            val result = cartManager.addProductToCart(uiState.value.productId)
            if (result is Response.Success)
                _uiState.update { it.copy(errorCart = false, quantity = it.quantity + 1) }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isCartLoading = false) }
        }
    }

    private fun removeFromCart() {
        _uiState.update { it.copy(isCartLoading = true) }
        viewModelScope.launch {
            val result = cartManager.removeProductFromCart(uiState.value.productId)
            if (result is Response.Success)
                _uiState.update { it.copy(errorCart = false, quantity = it.quantity - 1) }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isCartLoading = false) }
        }
    }

}
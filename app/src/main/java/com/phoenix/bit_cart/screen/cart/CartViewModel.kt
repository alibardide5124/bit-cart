package com.phoenix.bit_cart.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.bit_cart.data.CartManager
import com.phoenix.bit_cart.data.CartResponse
import com.phoenix.bit_cart.data.Response
import com.phoenix.bit_cart.data.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val cartManager: CartManager
): ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()
    private val _cartItems = MutableStateFlow<List<CartItem>>(listOf())
    val cartItems = _cartItems.asStateFlow()

    init {
        retrieveUserCart()
    }

    fun onEvent(event: CartUiEvent) {
        when(event) {
            CartUiEvent.PlaceOrder ->
                placeOrder(uiState.value.name, uiState.value.address)

            is CartUiEvent.RemoveItem ->
                removeItem(event.productId)

            CartUiEvent.OnTryAgain ->
                retrieveUserCart()

            is CartUiEvent.OnAddressChange ->
                _uiState.update { it.copy(address = event.address) }

            is CartUiEvent.OnNameChange ->
                _uiState.update { it.copy(name = event.name) }
        }
    }

    fun checkProductQuantity(): Boolean {
        return cartItems.value.none { it.quantity > it.availableQuantity }
    }

    private fun retrieveUserCart() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = cartManager.getUserCart()
            if (result is CartResponse.Success)
                _cartItems.update { result.items }
            else
                _uiState.update { it.copy(isError = true) }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun placeOrder(name: String, address: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = cartManager.placeOrder(name, address)
            if (result is Response.Success)
                _uiState.update { it.copy(orderPlaced = true) }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun removeItem(productId: String) {
        viewModelScope.launch {
            val result = cartManager.deleteProductFromCart(productId)
            if (result is Response.Success)
                _cartItems.update { it - it.first { it.productId == productId } }
        }
    }

}
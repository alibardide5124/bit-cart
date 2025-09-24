package com.phoenix.bit_cart.screen.cart

sealed interface CartUiEvent {
    data object PlaceOrder: CartUiEvent
    data class RemoveItem(val productId: String): CartUiEvent
    data object OnTryAgain: CartUiEvent
    data class OnNameChange(val name: String): CartUiEvent
    data class OnAddressChange(val address: String): CartUiEvent
}
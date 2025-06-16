package com.phoenix.bit_cart.screen.order

sealed interface OrderUiEvent {
    data class CancelOrder(val orderId: String): OrderUiEvent
    data object OnTryAgain: OrderUiEvent
}
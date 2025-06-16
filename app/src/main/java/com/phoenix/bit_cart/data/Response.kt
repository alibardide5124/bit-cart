package com.phoenix.bit_cart.data

import com.phoenix.bit_cart.data.model.CartItem
import com.phoenix.bit_cart.data.model.OrderDetails
import com.phoenix.bit_cart.data.model.Product

sealed interface AuthResponse {
    data object Success: AuthResponse
    data class Error(val message: String?): AuthResponse
}

sealed interface CartResponse {
    data class Success(val items: List<CartItem>): CartResponse
    data class Error(val message: String?): CartResponse
}

sealed interface IntResponse {
    data class Success(val result: Int): IntResponse
    data class Error(val message: String?): IntResponse
}

sealed interface Response {
    data object Success: Response
    data class Error(val message: String?): Response
}

sealed interface OrderResponse {
    data class Success(val orders: List<OrderDetails>): OrderResponse
    data class Error(val message: String?): OrderResponse
}

sealed interface ProductResponse {
    data class Success(val products: List<Product>): ProductResponse
    data class Error(val message: String?): ProductResponse
}

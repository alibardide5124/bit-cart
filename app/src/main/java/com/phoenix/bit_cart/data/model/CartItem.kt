package com.phoenix.bit_cart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    @SerialName("cart_id")
    val cartId: String,
    @SerialName("product_id")
    val productId: String,
    @SerialName("product_name")
    val productName: String,
    val quantity: Int,
    val price: Float,
    val available: Boolean
)

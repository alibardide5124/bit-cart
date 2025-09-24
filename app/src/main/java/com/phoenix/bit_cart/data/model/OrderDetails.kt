package com.phoenix.bit_cart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    @SerialName("product_id")
    val productId: String,

    @SerialName("quantity")
    val quantity: Int,

    @SerialName("product_name")
    val productName: String,

    @SerialName("price_at_purchase")
    val priceAtPurchase: Double
)

@Serializable
data class OrderDetails(
    @SerialName("order_id")
    val orderId: String,

    @SerialName("created_at")
    val dateCreated: String,

    @SerialName("name")
    val name: String,

    @SerialName("shipping_address")
    val shippingAddress: String,

    @SerialName("total_amount")
    val totalAmount: Double,

    @SerialName("status")
    val status: String,

    @SerialName("products")
    val products: List<OrderItem>
)
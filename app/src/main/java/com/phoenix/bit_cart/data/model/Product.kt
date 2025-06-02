package com.phoenix.bit_cart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String?,
    val sku: String,
    val price: Float,
    val stock: Int,
    val available: Boolean,
    @SerialName("image_url")
    val imageUrl: List<String>?,
    @SerialName("category_id")
    val categoryId: String,
    @SerialName("category_name")
    val categoryName: String,
    @SerialName("created_at")
    val createdAt: String
)
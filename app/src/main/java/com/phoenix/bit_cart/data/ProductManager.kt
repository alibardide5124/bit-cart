package com.phoenix.bit_cart.data

import com.phoenix.bit_cart.data.model.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

sealed interface ProductResponse {
    data class Success(val products: List<Product>): ProductResponse
    data class Error(val message: String?): ProductResponse
}

class ProductManager(
    val supabase: SupabaseClient
){

    suspend fun getAllProducts(): ProductResponse {
        try {
            val response = supabase.postgrest.rpc(function = "get_products").decodeList<Product>()
            return ProductResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return ProductResponse.Error(e.message)
        }
    }

}
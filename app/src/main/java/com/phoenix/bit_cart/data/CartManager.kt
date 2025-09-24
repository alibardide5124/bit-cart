package com.phoenix.bit_cart.data

import com.phoenix.bit_cart.data.model.CartItem
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

class CartManager(
    val supabase: SupabaseClient
){

    suspend fun getUserCartCount(): IntResponse {
        try {
            val response = supabase.postgrest
                .rpc(function = "get_user_cart_count")
                .decodeAs<Int>()
            return IntResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return IntResponse.Error(e.message)
        }
    }

    suspend fun getUserCart(): CartResponse {
        try {
            val response = supabase.postgrest
                .rpc(function = "get_user_cart")
                .decodeList<CartItem>()
            return CartResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return CartResponse.Error(e.message)
        }
    }

    suspend fun placeOrder(name: String, address: String): Response {
        try {
            supabase.postgrest
                .rpc(
                    function = "place_order",
                    parameters = buildJsonObject {
                        put("p_name", JsonPrimitive(name))
                        put("p_address", JsonPrimitive(address))
                    }
                )
            return Response.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message)
        }
    }

    suspend fun deleteProductFromCart(productId: String): Response {
        try {
            supabase.postgrest
                .rpc(
                    function = "delete_product_from_cart",
                    parameters = buildJsonObject { put("p_product_id", JsonPrimitive(productId)) }
                )
            return Response.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message)
        }
    }

    suspend fun getProductQuantityInCart(productId: String): IntResponse {
        try {
            val response = supabase.postgrest
                .rpc(
                    function = "get_product_quantity_in_cart",
                    parameters = buildJsonObject { put("p_product_id", JsonPrimitive(productId)) }
                )
                .decodeAs<Int>()
            return IntResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return IntResponse.Error(e.message)
        }
    }

    suspend fun addProductToCart(productId: String): Response {
        try {
            supabase.postgrest
                .rpc(
                    function = "add_product_to_cart",
                    parameters = buildJsonObject { put("p_product_id", JsonPrimitive(productId)) }
                )
            return Response.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message)
        }
    }

    suspend fun removeProductFromCart(productId: String): Response {
        try {
            supabase.postgrest
                .rpc(
                    function = "remove_product_from_cart",
                    parameters = buildJsonObject { put("p_product_id", JsonPrimitive(productId)) }
                )
            return Response.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message)
        }
    }


}

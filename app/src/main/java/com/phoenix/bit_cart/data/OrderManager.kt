package com.phoenix.bit_cart.data

import com.phoenix.bit_cart.data.model.OrderDetails
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

class OrderManager(
    val supabase: SupabaseClient
){

    suspend fun getOrderDetails(): OrderResponse {
        try {
            val response = supabase.postgrest
                .rpc(function = "get_all_order_details",)
                .decodeList<OrderDetails>()
            return OrderResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return OrderResponse.Error(e.message)
        }
    }

    suspend fun getUserOpenOrdersCount(): IntResponse {
        try {
            val response = supabase.postgrest
                .rpc(function = "count_user_open_orders")
                .decodeAs<Int>()
            return IntResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return IntResponse.Error(e.message)
        }
    }

    suspend fun cancelOrder(orderId: String): Response {
        try {
            supabase.postgrest
                .rpc(
                    function = "cancel_order",
                    parameters = buildJsonObject { put("p_order_id", JsonPrimitive(orderId)) }
                )
            return Response.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message)
        }
    }

}
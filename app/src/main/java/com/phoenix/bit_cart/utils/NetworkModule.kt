package com.phoenix.bit_cart.utils

import com.phoenix.bit_cart.data.AuthManager
import com.phoenix.bit_cart.data.CartManager
import com.phoenix.bit_cart.data.OrderManager
import com.phoenix.bit_cart.data.ProductManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSupabase() =
        createSupabaseClient(
            supabaseUrl = Constants.SUPABASE_URL,
            supabaseKey = Constants.SUPABASE_KEY
        ) {
            defaultSerializer = KotlinXSerializer(Json)
            install(Postgrest)
            install(Auth)
        }

    @Provides
    @Singleton
    fun provideAuthManager(supabase: SupabaseClient) =
        AuthManager(supabase)

    @Provides
    @Singleton
    fun provideProductManager(supabase: SupabaseClient) =
        ProductManager(supabase)

    @Provides
    @Singleton
    fun provideCartManager(supabase: SupabaseClient) =
        CartManager(supabase)

    @Provides
    @Singleton
    fun provideOrderManager(supabase: SupabaseClient) =
        OrderManager(supabase)

}
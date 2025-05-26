package com.phoenix.bit_cart.utils

import android.content.Context
import com.phoenix.bit_cart.data.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAuthManager(@ApplicationContext context: Context, supabase: SupabaseClient) =
        AuthManager(context, supabase)

}
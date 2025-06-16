package com.phoenix.bit_cart.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.phoenix.bit_cart.utils.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest
import java.util.UUID

class AuthManager(
    val supabase: SupabaseClient
) {

    suspend fun isLoggedIn(): Boolean = runBlocking {
        supabase.auth.awaitInitialization()
        return@runBlocking supabase.auth.currentUserOrNull() != null
    }


    fun getUserInfo(): UserInfo? =
        supabase.auth.currentUserOrNull()

    suspend fun logout(): AuthResponse {
        try {
            supabase.auth.signOut()
            return AuthResponse.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return AuthResponse.Error(e.message)
        }
    }
    suspend fun signUpWithEmail(username: String, password: String): AuthResponse {
        try {
            supabase.auth.signUpWith(Email) {
                this.email = username
                this.password = password
            }
            return AuthResponse.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return AuthResponse.Error(e.message)
        }
    }

    suspend fun signInWithEmail(username: String, password: String): AuthResponse {
        try {
            supabase.auth.signInWith(Email) {
                this.email = username
                this.password = password
            }
            return AuthResponse.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return AuthResponse.Error(e.message)
        }
    }

    fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
    }

    suspend fun loginGoogleUser(context: Context): AuthResponse {
        val hashedNonce = createNonce()

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(Constants.SERVER_CLIENT_ID)
            .setNonce(hashedNonce)
            .setAutoSelectEnabled(false)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val credentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(context)

        try {
            val result = credentialManager.getCredential(
                context = context,
                request = credentialRequest
            )
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(result.credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            supabase.auth.signInWith(IDToken) {
                idToken = googleIdToken
                provider = Google
            }
            return AuthResponse.Success
        } catch (e: Exception) {
            e.printStackTrace()
            return AuthResponse.Error(e.message)
        }
    }

}
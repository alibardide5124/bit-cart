package com.phoenix.bit_cart.screen.home

data class HomeUiState(
    val isLoading: LoadingStatus = LoadingStatus.LOADING,
    val isLoggedIn: Boolean = false,
    val isAuthLoading: Boolean = false,
    val email: String = "",
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val cartCount: Int = 0,
    val orderCount: Int = 0
)

enum class LoadingStatus {
    LOADING, DONE, FAILED
}
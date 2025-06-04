package com.phoenix.bit_cart.screen.home

data class HomeUiState(
    val isLoading: LoadingStatus = LoadingStatus.LOADING,
    val isSearching: Boolean = false,
    val searchQuery: String = ""
)

enum class LoadingStatus {
    LOADING, DONE, FAILED
}
package com.phoenix.bit_cart.screen.home

data class HomeUiState(
    val isLoading: LoadingStatus = LoadingStatus.LOADING
)

enum class LoadingStatus {
    LOADING, DONE, FAILED
}
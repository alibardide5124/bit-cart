package com.phoenix.bit_cart.screen.home

data class HomeUiState(
    val isLoading: LoadingStatus = LoadingStatus.LOADING,
    val isLoggedIn: Boolean = false,
    val isAuthLoading: Boolean = false,
    val email: String = "",
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val cartCount: Int = 0,
    val orderCount: Int = 0,
    val sortProperties: SortProperties =
        SortProperties(value = SortBy.NEWEST, type = SortType.ASCENDING)
)

enum class LoadingStatus {
    LOADING, DONE, FAILED
}

data class SortProperties(
    val value: SortBy,
    val type: SortType
)

enum class SortBy(val value: String) {
    NAME("Name"),
    NEWEST("Newest"),
    PRICE("Price"),
    CATEGORY("Category")
}

enum class SortType(val type: String) {
    ASCENDING("Ascending"),
    DESCENDING("Descending")
}
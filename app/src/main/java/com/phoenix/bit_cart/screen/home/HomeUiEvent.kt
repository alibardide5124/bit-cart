package com.phoenix.bit_cart.screen.home

sealed interface HomeUiEvent {
    data object Refresh: HomeUiEvent
    data object RefreshCart: HomeUiEvent
    data object StartSearch: HomeUiEvent
    data class OnSearchQueryChanged(val query: String): HomeUiEvent
    data object CloseSearch: HomeUiEvent
    data object Logout: HomeUiEvent
}
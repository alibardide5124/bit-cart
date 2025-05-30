package com.phoenix.bit_cart.screen.home

sealed interface HomeUiEvent {
    data object Refresh: HomeUiEvent
}
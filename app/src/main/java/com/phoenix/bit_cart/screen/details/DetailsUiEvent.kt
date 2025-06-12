package com.phoenix.bit_cart.screen.details

sealed interface DetailsUiEvent {
    data object AddToCart: DetailsUiEvent
    data object OnClickImage: DetailsUiEvent
    data object OnCloseImage: DetailsUiEvent
}
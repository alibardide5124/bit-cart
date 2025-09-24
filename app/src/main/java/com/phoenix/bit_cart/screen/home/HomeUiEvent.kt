package com.phoenix.bit_cart.screen.home

sealed interface HomeUiEvent {
    data object Refresh: HomeUiEvent
    data object RefreshCart: HomeUiEvent
    data object StartSearch: HomeUiEvent
    data class OnSearchQueryChanged(val query: String): HomeUiEvent
    data object CloseSearch: HomeUiEvent
    data class OpenDialog(val dialogType: HomeDialogType): HomeUiEvent
    data object CloseDialog: HomeUiEvent
    data class OnChooseCategory(val category: String): HomeUiEvent
    data class OnSort(val sortProperties: SortProperties): HomeUiEvent
    data object Logout: HomeUiEvent
}
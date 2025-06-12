package com.phoenix.bit_cart.screen.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(

): ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            DetailsUiEvent.AddToCart -> TODO()
            DetailsUiEvent.OnClickImage ->
                _uiState.update { it.copy(scaleImage = true) }

            DetailsUiEvent.OnCloseImage ->
                _uiState.update { it.copy(scaleImage = false) }

        }
    }

}
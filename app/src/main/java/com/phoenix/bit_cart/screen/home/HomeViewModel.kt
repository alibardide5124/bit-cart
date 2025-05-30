package com.phoenix.bit_cart.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.bit_cart.data.ProductManager
import com.phoenix.bit_cart.data.ProductResponse
import com.phoenix.bit_cart.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val productManager: ProductManager
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private val _products = MutableStateFlow(listOf<Product>())
    val products = _products.asStateFlow()

    init {
        getAllProducts()
    }

    fun onEvent(event: HomeUiEvent) {
        when(event) {
            HomeUiEvent.Refresh ->
                getAllProducts()
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = LoadingStatus.LOADING) }
            val result = productManager.getAllProducts()
            when(result) {
                is ProductResponse.Error -> {
                    _uiState.update { it.copy(isLoading = LoadingStatus.FAILED) }
                    Log.e("Network Error", result.message.toString())
                }
                is ProductResponse.Success -> {
                    _uiState.update { it.copy(isLoading = LoadingStatus.DONE) }
                    _products.update { result.products }
                }
            }
        }
    }

}
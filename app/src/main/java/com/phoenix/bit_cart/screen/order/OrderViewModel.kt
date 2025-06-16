package com.phoenix.bit_cart.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.bit_cart.data.OrderManager
import com.phoenix.bit_cart.data.OrderResponse
import com.phoenix.bit_cart.data.Response
import com.phoenix.bit_cart.data.model.OrderDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val orderManager: OrderManager
): ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState = _uiState.asStateFlow()
    private val _orders = MutableStateFlow<List<OrderDetails>>(listOf())
    val orders = _orders.asStateFlow()

    init {
        retrieveOrders()
    }

    fun onEvent(event: OrderUiEvent) {
        when(event) {
            is OrderUiEvent.CancelOrder ->
                cancelOrder(event.orderId)

            OrderUiEvent.OnTryAgain ->
                retrieveOrders()
        }
    }

    private fun retrieveOrders() {
        _uiState.update { it.copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            val result = orderManager.getOrderDetails()
            if (result is OrderResponse.Success)
                _orders.update { result.orders }
            else
                _uiState.update { it.copy(isError = true) }
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun cancelOrder(orderId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = orderManager.cancelOrder(orderId)
            if (result is Response.Success)
                retrieveOrders()
        }.invokeOnCompletion {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

}
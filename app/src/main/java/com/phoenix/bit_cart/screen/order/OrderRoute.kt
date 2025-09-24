package com.phoenix.bit_cart.screen.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderRoute(
    orderViewModel: OrderViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by orderViewModel.uiState.collectAsStateWithLifecycle()
    val orders by orderViewModel.orders.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    var orderId by remember { mutableStateOf("") }

    OrderScreen(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        orderDetails = orders,
        onTryAgain = { orderViewModel.onEvent(OrderUiEvent.OnTryAgain) },
        onCancel = {
            orderId = it
            coroutineScope.launch {
                sheetState.show()
            }
                   },
        onClickBack = { navigateToHome() }
    )

    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }
            },
            sheetState = sheetState
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Are you sure to cancel this order")
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            orderViewModel.onEvent(OrderUiEvent.CancelOrder(orderId))
                            coroutineScope.launch { sheetState.hide() }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Yes, cancel")
                    }
                    Spacer(Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch { sheetState.hide() }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("No, abort")
                    }
                }
            }
        }
    }
}
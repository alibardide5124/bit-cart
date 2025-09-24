package com.phoenix.bit_cart.screen.cart

import android.icu.text.DecimalFormat
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartRoute(
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val df = remember { DecimalFormat("#.##") }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(uiState.orderPlaced) {
        if (uiState.orderPlaced == true) {
            Toast.makeText(context, "Order placed successfully", Toast.LENGTH_LONG).show()
            navigateToHome()
        }
    }

    CartScreen(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        onTryAgain = { cartViewModel.onEvent(CartUiEvent.OnTryAgain) },
        cartItems = cartItems,
        onClickBack = navigateToHome,
        onClickPlaceOrder = {
            if (cartViewModel.checkProductQuantity())
                coroutineScope.launch { sheetState.show() }
        },
        onClickRemove = { cartViewModel.onEvent(CartUiEvent.RemoveItem(it)) }
    )

    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }
            },
            sheetState = sheetState
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { cartViewModel.onEvent(CartUiEvent.OnNameChange(it)) },
                    placeholder = {
                        Text("Name")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.address,
                    onValueChange = { cartViewModel.onEvent(CartUiEvent.OnAddressChange(it)) },
                    placeholder = {
                        Text("Address")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        cartViewModel.onEvent(CartUiEvent.PlaceOrder)
                        coroutineScope.launch { sheetState.hide() }
                              },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pay ${df.format(cartItems.sumOf { it.quantity * it.price.toDouble() })} + Tax /w Shipping")
                }
            }
        }
    }
}
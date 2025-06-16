package com.phoenix.bit_cart.screen.order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phoenix.bit_cart.data.model.OrderDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    isLoading: Boolean,
    isError: Boolean,
    orderDetails: List<OrderDetails>,
    onTryAgain: () -> Unit,
    onCancel: (orderId: String) -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back to home",
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { onClickBack() }
                            .padding(8.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(!isLoading) {
                LazyColumn(Modifier.fillMaxSize()){
                    items(orderDetails, key = { it.orderId }) {
                        OrderDetailWidget(
                            modifier = Modifier.fillMaxWidth(),
                            orderDetails = it,
                            onClickCancel = { onCancel(it.orderId) }
                        )
                    }
                }
            }
            AnimatedVisibility(!isLoading && orderDetails.isEmpty() && !isError, modifier = Modifier.align(Alignment.Center)) {
                Text("No order available. Visit cart to place order.")
            }
            AnimatedVisibility(isLoading, modifier = Modifier.align(Alignment.Center)) {
                CircularProgressIndicator()
            }
            AnimatedVisibility(isError, modifier = Modifier.align(Alignment.Center)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error while loading orders :(",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { onTryAgain() },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Try Again")
                    }
                }
            }
        }
    }
}
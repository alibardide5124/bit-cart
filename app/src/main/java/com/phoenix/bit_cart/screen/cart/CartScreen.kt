package com.phoenix.bit_cart.screen.cart

import android.icu.text.DecimalFormat
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phoenix.bit_cart.data.model.CartItem
import com.phoenix.bit_cart.screen.cart.components.CartItemWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    isLoading: Boolean,
    isError: Boolean,
    onTryAgain: () -> Unit,
    cartItems: List<CartItem>,
    onClickBack: () -> Unit,
    onClickPlaceOrder: () -> Unit,
    onClickRemove: (productId: String) -> Unit
) {
    val df = remember { DecimalFormat("#.##") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("سبد خرید") },
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
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(!isLoading) {
                Column {
                    LazyColumn(Modifier.fillMaxSize().weight(1f)){
                        items(cartItems, key = { it.productId }) {
                            CartItemWidget(
                                modifier = Modifier.fillMaxWidth(),
                                cartItem = it,
                                onClickRemove = { onClickRemove(it) }
                            )
                        }
                    }
                    if(cartItems.isNotEmpty()) {
                        Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                            Text("هزینه کلی ${df.format(cartItems.sumOf { it.quantity * it.price.toDouble() })}")
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = { onClickPlaceOrder() },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("ثبت سفارش")
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(!isLoading && cartItems.isEmpty() && !isError, modifier = Modifier.align(Alignment.Center)) {
                Text("""
                    هیچ محصولی به سبد خرید اضافه نشده.
                """.trimIndent())
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
                        text = "خطا در حین بارگیری سبد خرید",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { onTryAgain() },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("تلاش مجدد")
                    }
                }
            }
        }
    }
}
package com.phoenix.bit_cart.screen.order

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phoenix.bit_cart.data.model.OrderDetails

@Composable
fun OrderDetailWidget(
    modifier: Modifier = Modifier,
    orderDetails: OrderDetails,
    onClickCancel: () -> Unit
) {
    Column(
        modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
    ) {
        orderDetails.products.forEach {
            Text("${it.quantity}x ${it.productName}")
            Spacer(Modifier.height(4.dp))
        }
        Text(
            text = "Order placed at ${orderDetails.dateCreated}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Receiver name: ${orderDetails.name}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Address: ${orderDetails.shippingAddress}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Total cost: ${orderDetails.totalAmount}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Status: ${orderDetails.status}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        if (listOf("finished", "received", "canceled").none { it == orderDetails.status }) {
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = { onClickCancel() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancel order")
            }
        }
    }
}
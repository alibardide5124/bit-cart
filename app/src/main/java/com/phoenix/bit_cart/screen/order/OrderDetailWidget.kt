package com.phoenix.bit_cart.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phoenix.bit_cart.data.model.OrderDetails
import com.phoenix.bit_cart.utils.toHumanReadableDate

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
            Text("${it.quantity} عدد ${it.productName}")
            Spacer(Modifier.height(4.dp))
        }
        Text(
            text = "سفارش ثبت شده در ${orderDetails.dateCreated.toHumanReadableDate()}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "نام گیرنده: ${orderDetails.name}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "آدرس: ${orderDetails.shippingAddress}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "هزینه کل: ${orderDetails.totalAmount}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "وضعیت:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.width(8.dp))
            when(orderDetails.status) {
                "pending" ->
                    Text(
                        text = "در انتظار",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .padding(4.dp)
                    )

                "active" ->
                    Text(
                        text = "در حال پردازش",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp))
                            .padding(4.dp)
                    )

                "shipped" ->
                    Text(
                        text = "ارسال شده",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp))
                            .padding(4.dp)
                    )

                "finished" ->
                    Text(
                        text = "تمام شده",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp))
                            .padding(4.dp)
                    )

                "cancelled" ->
                    Text(
                        text = orderDetails.status,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.onError)
                            .padding(4.dp)
                    )
            }
        }
        if (listOf("finished", "canceled").none { it == orderDetails.status }) {
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = { onClickCancel() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("لغو سفارش")
            }
        }
    }
}
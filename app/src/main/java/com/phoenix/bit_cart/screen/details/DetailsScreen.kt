package com.phoenix.bit_cart.screen.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.phoenix.bit_cart.data.model.Product
import com.phoenix.bit_cart.ui.theme.BitCartTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    product: Product?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back to home"
                    )
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(product != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        if (product!!.available) {
                            Text(
                                text = "${product.price}$",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${product.stock} unit available",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = "Out of stock",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    enabled = product!!.available
                ) {
                    Text("Add to cart")
                }
            }
        }
    ) { innerPadding ->
        Crossfade(product != null) { target ->
            if (!target) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Box(
                        modifier = Modifier.aspectRatio(2 / 1f)
                    ) {
                        product!!.imageUrl?.get(0).let {
                            AsyncImage(
                                model = it,
                                contentDescription = null
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = .4f)
                                        )
                                    )
                                )
                        )
                        Text(
                            text = product.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(vertical = 12.dp, horizontal = 16.dp)
                        )
                    }
                    product!!.description?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsPreview() {
    BitCartTheme {
        DetailsScreen(
            Product(
                id = "1234",
                name = "Gigabyte B550 WiFi",
                description = "This is a shitboard made by shitty company",
                sku = "GB-M-B550-W",
                price = 299.9f,
                stock = 6,
                available = true,
                imageUrl = null,
                categoryId = "MBD",
                categoryName = "Motherboards",
                createdAt = "Sometime"
            )
        )
    }
}
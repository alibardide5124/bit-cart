package com.phoenix.bit_cart.screen.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.phoenix.bit_cart.data.model.Product

@Composable
fun DetailsRoute(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    product: Product,
    navigateBack: () -> Unit
) {
    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detailsViewModel.init(product.id)
    }

    BackHandler(uiState.scaleImage) {
        detailsViewModel.onEvent(DetailsUiEvent.OnCloseImage)
    }

    DetailsScreen(
        product = product,
        onClickBack = { navigateBack() },
        onClickImage = { detailsViewModel.onEvent(DetailsUiEvent.OnClickImage) },
        quantity = uiState.quantity,
        isCartLoading = uiState.isCartLoading,
        errorCart = uiState.errorCart,
        addToCart = { detailsViewModel.onEvent(DetailsUiEvent.AddToCart) },
        removeFromCart = { detailsViewModel.onEvent(DetailsUiEvent.RemoveFromCart) },
    )
    AnimatedVisibility(uiState.scaleImage, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                .clickable { detailsViewModel.onEvent(DetailsUiEvent.OnCloseImage) }
                .padding(16.dp)
        ) {
            product.imageUrl?.get(0).let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
    }
}
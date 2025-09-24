package com.phoenix.bit_cart.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phoenix.bit_cart.R
import com.phoenix.bit_cart.data.model.Product
import com.phoenix.bit_cart.screen.home.component.HomeProductWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isLoading: LoadingStatus,
    isSearching: Boolean,
    searchQuery: String,
    products: List<Product>,
    onTryAgain: () -> Unit,
    onClickDrawerMenu: () -> Unit,
    onClickSearch: () -> Unit,
    onClickProduct: (Product) -> Unit,
    onClickCloseSearch: () -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            Crossfade(isSearching) { target ->
                if (target) {
                    HomeScreenSearch(
                        searchQuery,
                        onClickCloseSearch,
                        onSearchQueryChange
                    )
                } else {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(id = R.string.app_name)) },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Drawer",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onClickDrawerMenu() }
                                    .padding(8.dp)
                            )
                        },
                        actions = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onClickSearch() }
                                    .padding(8.dp)
                            )
                        }
                    )
                }
            }

        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (isLoading) {
                LoadingStatus.LOADING ->
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LinearProgressIndicator()
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Loading Products...",
                                fontSize = 16.sp
                            )
                        }
                    }

                LoadingStatus.DONE -> {
                    if (products.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No Products Found :(",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    items(
                        items =
                            if (searchQuery.isBlank()) products
                            else products.filter {
                                "${it.name} ${it.description} ${it.categoryName}"
                                    .contains(searchQuery.lowercase())
                            },
                        key = { it.id }
                    ) {
                        HomeProductWidget(
                            modifier = Modifier.fillMaxWidth(),
                            product = it,
                            onClick = { onClickProduct(it) }
                        )
                    }
                }

                LoadingStatus.FAILED -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error while loading products :(",
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenSearch(
    searchQuery: String,
    onClickCloseSearch: () -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Drawer",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClickCloseSearch() }
                .padding(8.dp)
        )
        Spacer(Modifier.width(8.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChange(it) },
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            trailingIcon = {
                if (searchQuery.isNotEmpty())
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Drawer",
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSearchQueryChange("") }
                            .padding(8.dp)
                    )
            }
        )
    }
}
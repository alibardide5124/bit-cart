package com.phoenix.bit_cart.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phoenix.bit_cart.data.model.Product
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToAbout: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToDetails: (Product) -> Unit,
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val products by homeViewModel.products.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    BackHandler(uiState.isSearching) {
        homeViewModel.onEvent(HomeUiEvent.CloseSearch)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(.8f)
            ) {
                Text(
                    text = "Bit-Cart",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(Modifier.height(16.dp))
                if (uiState.isLoggedIn) {
                    Text(
                        text = uiState.email,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    OutlinedButton(
                        onClick = { homeViewModel.onEvent(HomeUiEvent.Logout) },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp).width(144.dp)
                    ) {
                        AnimatedVisibility(uiState.isAuthLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        }
                        AnimatedVisibility(!uiState.isAuthLoading) {
                            Text("Logout")
                        }
                    }
                } else {
                    Button(
                        onClick = navigateToLogin,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp).width(144.dp)
                    ) {
                        Text("Login")
                    }
                }
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(16.dp))
                NavigationDrawerItem(
                    label = { Text("Cart") },
                    selected = false,
                    onClick = {
                        if (uiState.isLoggedIn)
                            navigateToCart()
                        else
                            navigateToLogin()
                    },
                    icon = {
                        Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("About") },
                    selected = false,
                    onClick = {
                        navigateToAbout()
                        coroutineScope.launch { drawerState.close() }
                    },
                    icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
                )
            }
        }
    ) {
        HomeScreen(
            isLoading = uiState.isLoading,
            isSearching = uiState.isSearching,
            searchQuery = uiState.searchQuery,
            products = products,
            onTryAgain = { homeViewModel.onEvent(HomeUiEvent.Refresh) },
            onClickDrawerMenu = { coroutineScope.launch { drawerState.open() } },
            onClickSearch = { homeViewModel.onEvent(HomeUiEvent.StartSearch) },
            onClickProduct = { navigateToDetails(it) },
            onClickCloseSearch = { homeViewModel.onEvent(HomeUiEvent.CloseSearch) },
            onSearchQueryChange = { homeViewModel.onEvent(HomeUiEvent.OnSearchQueryChanged(it))}
        )
    }
}

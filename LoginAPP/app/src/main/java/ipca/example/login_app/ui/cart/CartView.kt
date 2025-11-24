package ipca.example.login_app.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ipca.example.login_app.models.CartItem
import ipca.example.login_app.models.Product
import ipca.example.login_app.ui.components.BackTopBar
import ipca.example.login_app.ui.components.CartItemRow
import ipca.example.login_app.ui.theme.LoginAPPTheme
import kotlinx.coroutines.launch

@Composable
fun CartView(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    val viewModel: CartViewModel = viewModel()
    val uiState by viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    CartViewContent(
        modifier = modifier,
        navController = navController,
        uiState = uiState,
        cartItems = uiState.items,
        totalCart = viewModel.total(),
        onRemoveItem = { item -> viewModel.removeItem(item.product!!) },
        onFinalizeOrder = {
            coroutineScope.launch {
                viewModel.finalizeOrder()
            }
        },
        onQuantityChange = { item, change ->
            if (change == 1) {
                viewModel.addProduct(item.product!!)
            } else if (change == -1) {
                viewModel.removeUnit(item.product!!)
            }
        })
}

@Composable
fun CartViewContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiState: CartState,
    cartItems: List<CartItem>,
    onRemoveItem: (CartItem) -> Unit,
    onQuantityChange: (CartItem, Int) -> Unit,
    onFinalizeOrder: () -> Unit,
    totalCart: Double
) {
    Box(modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (uiState.error != null) {
            Text(
                uiState.error!!,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                BackTopBar(
                    title = "Detalhes do Carrinho",
                    navController = navController
                )

                if (cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "O seu carrinho está vazio!",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        items(cartItems) { item ->
                            CartItemRow(
                                item = item,
                                onRemoveItem = { onRemoveItem(item) },
                                onQuantityChange = { change -> onQuantityChange(item, change) })
                        }

                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total do Carrinho",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "€ ${String.format("%.2f", totalCart)}",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Button(
                        onClick = onFinalizeOrder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                            .height(56.dp)
                    ) {
                        Text("Finalizar Compra", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartViewPreview() {
    LoginAPPTheme {
        CartViewContent(
            navController = NavHostController(LocalContext.current),
            uiState = CartState(
                items = listOf(
                    CartItem(Product("1", "Product 1", "Description 1", 10.99, "Category 1", "", 4), 2),
                    CartItem(Product("2", "Product 2", "Description 2", 20.49, "Category 2", "", 4), 2),
                )
            ),
            cartItems = listOf(
                CartItem(Product("1", "Product 1", "Description 1", 10.99, "Category 1", "", 4), 2),
                CartItem(Product("2", "Product 2", "Description 2", 20.49, "Category 2", "", 4), 1)
            ),
            onRemoveItem = {},
            onQuantityChange = { _, _ -> },
            onFinalizeOrder = {},
            totalCart = (10.99 * 2) + 20.49
        )
    }
}
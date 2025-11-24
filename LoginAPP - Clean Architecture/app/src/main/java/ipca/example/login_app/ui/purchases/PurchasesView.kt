package ipca.example.login_app.ui.purchases

import PurchaseItemRow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ipca.example.login_app.ui.cart.CartState
import ipca.example.login_app.ui.components.BackTopBar
import ipca.example.login_app.ui.theme.LoginAPPTheme


@Composable
fun PurchasesView(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    val viewModel: PurchasesViewModel = viewModel()
    val uiState by viewModel.uiState

    PurchasesViewContent(modifier, navController, uiState)

    LaunchedEffect(Unit) {
        viewModel.fetchPurchases()
    }
}

@Composable
fun PurchasesViewContent(
    modifier: Modifier = Modifier, navController: NavHostController, uiState: PurchasesState
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else if (uiState.error != null) {
            Text(
                uiState.error.toString(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {

                BackTopBar(title = "Minhas Compras", navController = navController)

                if (uiState.purchases.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Não tem nenhuma compra.",
                            color = Color.Gray
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "€ ${String.format("%.2f", uiState.total)}",
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Valor total das suas compras",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(uiState.purchases) { product ->
                                PurchaseItemRow(product = product)
                                Divider(color = Color.DarkGray, thickness = 0.5.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PurchasesViewPreview() {
    LoginAPPTheme {
        PurchasesViewContent(navController = NavHostController(LocalContext.current) , uiState = PurchasesState())
    }
}
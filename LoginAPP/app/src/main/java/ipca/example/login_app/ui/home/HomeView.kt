package ipca.example.login_app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ipca.example.login_app.models.Product
import ipca.example.login_app.ui.components.TopBar
import ipca.example.login_app.ui.components.ProductViewCell
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun HomeView(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState

    HomeViewContent(modifier, uiState, navController)

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }
}

@Composable
fun HomeViewContent(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    navController: NavHostController
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
            TopBar(modifier = Modifier, navController = navController)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(
                    items = uiState.products
                ) { index, product ->
                    ProductViewCell(product)
                    {
                        navController.navigate("product/${product.id}")
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    LoginAPPTheme {
        val navController = NavHostController(LocalContext.current)
        val products = remember {
            listOf(
                Product("1", "Product 1", "Description 1",10.99,  "Category 1", "", 4),
                Product("2", "Product 2", "Description 2", 20.49, "Category 2", "", 4),
                Product("3", "Product 3", "Description 3",5.00,  "Category 1", "", 5)
            )
        }
        HomeViewContent(
            uiState = HomeState(
                products = products,
                isLoading = false
            ),
            navController = navController
        )
    }
}
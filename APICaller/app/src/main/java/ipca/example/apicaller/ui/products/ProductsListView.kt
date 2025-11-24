package ipca.example.apicaller.ui.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ipca.example.apicaller.models.encodeUrl
import ipca.example.apicaller.models.Product
import ipca.example.apicaller.ui.theme.APICallerTheme


@Composable
fun ProductsViewList(modifier: Modifier = Modifier, navController: NavController,category:  String) {
    val viewProduct: ProductsListViewModel = viewModel()
    val uiState by viewProduct.uiState

    ProductsListViewContent(modifier = modifier, uiState = uiState, navController = navController)

    LaunchedEffect(Unit) {
        viewProduct.fetchProducts(category)
    }
}

@Composable
fun ProductsListViewContent(modifier: Modifier = Modifier, uiState: ProductsList, navController: NavController) {
    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else if (uiState.error != null) {
        Text(
            uiState.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(uiState.products) { _, product ->
                ProductViewCell(product) {
                    product.title?.let { title ->
                        navController.navigate("product_detail/${product.id}")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ProductsListViewPreview() {
    APICallerTheme {
        ProductsListViewContent(
            modifier = Modifier.padding(10.dp),
            uiState = ProductsList(
                products = listOf(Product(title = "Product 1", price = 10.0, id = 1))
            ),
            navController = NavController(androidx.compose.ui.platform.LocalContext.current)
        )
    }
}
package ipca.example.login_app.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import ipca.example.login_app.ui.cart.CartViewModel
import ipca.example.login_app.ui.components.BackTopBar
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun ProductDetailView(
    modifier: Modifier = Modifier, navController: NavHostController, idProduct: String
) {
    val viewModel: ProductDetailViewModel = viewModel()
    val viewModelCard: CartViewModel = viewModel()
    val uiState by viewModel.uiState
    val onAddToCart: () -> Unit = {
        val product = uiState.product
        if (product != null) {
            viewModelCard.addProduct(product)
        }
    }

    ProductDetailContent(modifier, uiState, navController, onAddToCart)

    LaunchedEffect(idProduct) {
        viewModel.fetchProduct(idProduct)
    }
}

@Composable
fun ProductDetailContent(
    modifier: Modifier = Modifier,
    uiState: ProductDetailState,
    navController: NavHostController,
    addCart: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.error != null) {
            Text(
                uiState.error.toString(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            Column {
                BackTopBar(
                    title = "Detalhes do Produto", navController = navController
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.LightGray), contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = uiState.product.image,
                            contentDescription = uiState.product.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .heightIn(max = 250.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = uiState.product.title ?: "",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )

                            uiState.product.rating?.let { rating ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "Rating",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = rating,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "€ ${uiState.product.price ?: "N/A"}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Descrição do Produto",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = uiState.product.description
                                ?: "Sem descrição detalhada disponível.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )

                        if (uiState.product.quantity != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Em Stock: ${uiState.product.quantity}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if ((uiState.product.quantity ?: 0) > 0) Color.Green.copy(
                                    alpha = 0.8f
                                ) else Color.Red
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        val isOutOfStock = (uiState.product.quantity ?: 0) <= 0

                        Button(
                            onClick = addCart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !isOutOfStock
                        ) {
                            Text(
                                "Adicionar ao Carrinho",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    LoginAPPTheme {
        ProductDetailContent(
            uiState = ProductDetailState(),
            navController = NavHostController(LocalContext.current),
            addCart = {}
        )
    }
}



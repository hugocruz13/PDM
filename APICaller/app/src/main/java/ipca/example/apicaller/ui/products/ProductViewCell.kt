package ipca.example.apicaller.ui.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ipca.example.apicaller.models.Product
import ipca.example.apicaller.ui.theme.APICallerTheme

@Composable
fun ProductViewCell(product: Product, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = product.title ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            AsyncImage(
                model = product.urlToImage ?: "",
                contentDescription = product.title ?: "Product Image"
            )
            Text(
                text = "${product.price}€",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                textAlign = TextAlign.End,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ProductViewCellPreview() {
    APICallerTheme {
        ProductViewCell(
            product = Product(
                title = "Essence Mascara Lash Princess",
                urlToImage = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 9.99
            )
        )
    }
}

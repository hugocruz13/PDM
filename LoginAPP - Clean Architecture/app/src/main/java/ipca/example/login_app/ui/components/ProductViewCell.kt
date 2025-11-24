package ipca.example.login_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ipca.example.login_app.models.Product
import ipca.example.login_app.ui.theme.LoginAPPTheme

@Composable
fun ProductViewCell(
    product: Product,
    modifier: Modifier = Modifier,
    onclik: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clickable { onclik() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = product.image ?: "",
                contentDescription = product.title ?: "Sem Imagem",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.title ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${product.price ?: "N/A"} €",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductViewCellPreview() {
    LoginAPPTheme {
        ProductViewCell(
            product = Product(
                title = "Essence Mascara Lash Princess",
                price = 9.99,
                image = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp",
                )
        )
    }
}

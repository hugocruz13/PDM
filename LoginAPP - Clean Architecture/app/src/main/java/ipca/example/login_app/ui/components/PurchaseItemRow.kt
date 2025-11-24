import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.example.login_app.models.ProductUser
import ipca.example.login_app.ui.theme.LoginAPPTheme
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: Date?): String {
    return if (date != null) {
        val formatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("pt", "PT"))
        formatter.format(date)
    } else {
        "Data Desconhecida"
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PurchaseItemRow(product: ProductUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(56.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = MaterialTheme.shapes.small
        ) {
            AsyncImage(
                model = product.image, // URL da imagem
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.title ?: "Produto Desconhecido",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = formatDate(product.data),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Text(
            text = "€ ${String.format("%.2f", (product.price ?: 0.0) * (product.quantidade ?: 0))}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseItemRowPreview() {
    LoginAPPTheme {
        PurchaseItemRow(
            product = ProductUser(
                title = "Iphone",
                price = 999.99,
                image = "",
                data = Date(),
                quantidade = 1
            )
        )
    }
}
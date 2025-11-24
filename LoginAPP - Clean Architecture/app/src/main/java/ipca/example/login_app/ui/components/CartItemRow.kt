package ipca.example.login_app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.example.login_app.models.CartItem
import ipca.example.login_app.models.Product

import ipca.example.login_app.ui.theme.LoginAPPTheme
import java.util.Locale

@Composable
fun CartItemRow(
    item: CartItem,
    onRemoveItem: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = item.product?.image,
                contentDescription = item.product?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.product?.title ?: "Produto sem nome",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Preço: € %.2f".format(
                        Locale.getDefault(),
                        item.product?.price.toString().toDoubleOrNull() ?: 0.0
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Text(
                    text = "Subtotal: € %.2f".format(
                        Locale.getDefault(),
                        (item.product?.price.toString().toDoubleOrNull() ?: 0.0) * item.quantity
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(onClick = onRemoveItem) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover produto",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onQuantityChange(-1) },
                    enabled = item.quantity > 1
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Diminuir")
                }

                Text(
                    text = item.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = { onQuantityChange(1) },
                    enabled = item.quantity < (item.product?.quantity ?: Int.MAX_VALUE)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar")
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartItemRow(){
    LoginAPPTheme {
        CartItemRow(
            item = CartItem(
                product = Product(
                    id = "1",
                    title = "Sample Product",
                    price = 19.99,
                    image = "",
                    quantity = 10
                ),
                initialQuantity = 2
            ),
            {},
            {})
    }
}

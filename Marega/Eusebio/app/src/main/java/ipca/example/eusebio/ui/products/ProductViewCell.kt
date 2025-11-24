package ipca.example.eusebio.ui.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ipca.example.eusebio.models.Product


@Composable
fun ProductViewCell(
    product : Product,
    modifier: Modifier = Modifier
){
    Card (
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            ,
        shape = RoundedCornerShape(12.dp),

    ){
        Column (
            modifier = Modifier.padding(8.dp),

            ){
            Text(text = product.name ?: "",
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp)
            AsyncImage(
                model = product.image_url,
                contentDescription = product.description,

                )
            Text(text = product.description ?: "",
                modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}

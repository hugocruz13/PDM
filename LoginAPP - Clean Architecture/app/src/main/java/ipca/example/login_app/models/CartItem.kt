package ipca.example.login_app.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

data class CartItem(
    var product: Product? = null,
    var initialQuantity: Int = 1
){
    var quantity by mutableIntStateOf(initialQuantity)
}

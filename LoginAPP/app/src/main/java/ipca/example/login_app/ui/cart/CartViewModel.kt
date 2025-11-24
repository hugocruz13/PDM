package ipca.example.login_app.ui.cart

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import ipca.example.login_app.models.CartItem
import ipca.example.login_app.models.Product
import ipca.example.login_app.models.ProductUser
import ipca.example.login_app.room.AppDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

data class CartState(
    val items: List<CartItem> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartItemDao = AppDatabase.getInstance(application).cartItemDao()
    private val cartManager = CartManager(cartItemDao)

    var uiState = mutableStateOf(CartState(items = cartManager.items))
        private set

    val auth = Firebase.auth
    val db = Firebase.firestore


    init {
        loadCartItemsFromDb()
    }

    private fun loadCartItemsFromDb() {
        viewModelScope.launch {
            cartManager.loadCartItems()
            uiState.value = uiState.value.copy(isLoading = false, error = null)
        }
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        uiState.value = uiState.value.copy(isLoading = true, error = null)
        try {
            cartManager.addProduct(product)
            uiState.value = uiState.value.copy(isLoading = false)
        } catch (e: Exception) {
            uiState.value = uiState.value.copy(isLoading = false, error = e.message)
        }
    }

    fun removeUnit(product: Product) = viewModelScope.launch {
        try {
            cartManager.removeUnit(product)
            uiState.value = uiState.value.copy(error = null)
        } catch (e: Exception) {
            uiState.value = uiState.value.copy(error = e.message)
        }
    }

    fun removeItem(product: Product) = viewModelScope.launch {
        try {
            cartManager.removeItem(product)
            uiState.value = uiState.value.copy(error = null)
        } catch (e: Exception) {
            uiState.value = uiState.value.copy(error = e.message)
        }
    }

    fun total(): Double {
        return cartManager.total()
    }

    fun clearCart() = viewModelScope.launch {
        try {
            cartManager.clearCart()
            uiState.value = uiState.value.copy(error = null)
        } catch (e: Exception) {
            uiState.value = uiState.value.copy(error = e.message)
        }
    }

    fun finalizeOrder() = viewModelScope.launch {
        uiState.value = uiState.value.copy(isLoading = true, error = null)

        val userId = auth.currentUser?.uid
        if (userId == null) {
            uiState.value =
                uiState.value.copy(isLoading = false, error = "Utilizador não autenticado.")
            return@launch
        }

        val totalPrice = total()

        if (totalPrice == 0.0) {
            uiState.value = uiState.value.copy(isLoading = false, error = "Carrinho vazio.")
            return@launch
        }

        val userDocRef = db.collection("users").document(userId)

        try {
            db.runTransaction { transaction ->
                cartManager.items.forEach { cartItem ->
                    val product = cartItem.product
                    val quantityBought = cartItem.quantity
                    val productId = product?.id ?: throw Exception("Produto sem ID")

                    val inventoryRef = db.collection("inventory").document(productId)
                    val inventorySnapshot = transaction.get(inventoryRef)

                    val currentStock = inventorySnapshot.getLong("quantity")?.toInt() ?: 0

                    if (currentStock < quantityBought) {
                        throw Exception("Stock insuficiente para o produto: ${product?.title}")
                    }

                    val newStock = currentStock - quantityBought
                    transaction.update(inventoryRef, "quantity", newStock)

                    val productData = ProductUser(
                        title = cartItem.product?.title,
                        price = cartItem.product?.price,
                        image = cartItem.product?.image,
                        data = Date(),
                        quantidade = cartItem.quantity
                    )

                    transaction.update(userDocRef, "products", FieldValue.arrayUnion(productData))
                }

                transaction.update(userDocRef, "total", FieldValue.increment(totalPrice))

                null
            }.await()
            clearCart().join()

            uiState.value = uiState.value.copy(isLoading = false)

        } catch (e: Exception) {
            Log.e("CartViewModel", "Erro ao finalizar a encomenda: ${e.message}")
            uiState.value = uiState.value.copy(isLoading = false, error = e.message)
        }
    }
}

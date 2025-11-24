package ipca.example.login_app.ui.product

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ipca.example.login_app.models.Product

data class ProductDetailState(
    var product: Product = Product(),
    var error: String? = null,
    var isLoading: Boolean = false
)

class ProductDetailViewModel : ViewModel() {
    var uiState = mutableStateOf(ProductDetailState())
        private set

    val db = Firebase.firestore

    fun fetchProduct(idProduct: String) {
        uiState.value = uiState.value.copy(isLoading = true, error = null)

        val docRef = db.collection("inventory").document(idProduct)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val product = snapshot.toObject(Product::class.java)
                uiState.value = uiState.value.copy(
                    product = product!!,
                    isLoading = false,
                    error = null
                )
            } else {
                Log.d(TAG, "Product not found")
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Product not found"
                )
            }
        }
    }
}


package ipca.example.login_app.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ipca.example.login_app.models.Product

data class HomeState (
    var products : List<Product> = emptyList(),
    var error : String? = null,
    var isLoading : Boolean = false
)

class HomeViewModel : ViewModel() {

    var uiState = mutableStateOf(HomeState())
        private set

    val db = Firebase.firestore
    fun fetchProducts() {
        uiState.value = uiState.value.copy(isLoading = true)

        val docRef = db.collection("inventory")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
                return@addSnapshotListener
            }

            val fetchedProducts = mutableListOf<Product>()
            for (doc in snapshot?.documents?:emptyList()){
                val product = doc.toObject(Product::class.java)
                product?.let {
                    fetchedProducts.add(it)
                }

            }


            uiState.value = uiState.value.copy(
                products = fetchedProducts,
                isLoading = false,
                error = null
            )
        }
    }
}
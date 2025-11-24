package ipca.example.login_app.ui.purchases

import android.content.ContentValues.TAG
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.login_app.models.ProductUser
import com.google.firebase.Timestamp


data class PurchasesState(
    val purchases: List<ProductUser> = emptyList(),
    val total: Double = 0.0,
    var error: String? = null,
    var isLoading: Boolean = false
)

class PurchasesViewModel : ViewModel() {
    var uiState = mutableStateOf(PurchasesState())
        private set

    var auth = Firebase.auth
    val db = Firebase.firestore

    fun fetchPurchases() {
        uiState.value = uiState.value.copy(isLoading = true, error = null)

        val user = auth.currentUser
        if (user == null) {
            uiState.value = uiState.value.copy(
                isLoading = false, error = "Utilizador não autenticado."
            )
            return
        }

        val docRef = db.collection("users").document(user.uid)

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("PurchasesViewModel", "Listen failed.", e)
                uiState.value = uiState.value.copy(
                    isLoading = false, error = e.localizedMessage
                )
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                val total = snapshot.getDouble("total") ?: 0.0
                val purchasesData = snapshot.get("products") as? List<HashMap<String, Any>>
                val fetchedPurchases = purchasesData?.mapNotNull { purchaseMap ->
                    val dateTimestamp = purchaseMap["data"] as? Timestamp

                    ProductUser(
                        title = purchaseMap["title"] as? String ?: "",
                        price = (purchaseMap["price"] as? Number)?.toDouble() ?: 0.0,
                        image = purchaseMap["image"] as? String ?: "",
                        data = dateTimestamp?.toDate(),
                        quantidade = (purchaseMap["quantidade"] as? Number)?.toInt() ?: 0
                    )
                }
                uiState.value = uiState.value.copy(
                    purchases = fetchedPurchases ?: emptyList(),
                    total = total,
                    isLoading = false,
                    error = null
                )
            } else {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Documento do utilizador não encontrado."
                )
            }
        }
    }
}
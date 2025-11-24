package ipca.example.login_app.ui.perfil

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.login_app.models.Product
import ipca.example.login_app.models.User

data class PerfilState(
    var user: User = User(),
    var error: String? = null,
    var isLoading: Boolean = false
)

class PerfilViewModel : ViewModel()
{
    var uiState = mutableStateOf(PerfilState())
        private set

    var auth = Firebase.auth
    val db = Firebase.firestore

    fun fetchUser() {
        uiState.value = uiState.value.copy(isLoading = true, error = null)

        val docRef = db.collection("users").document(auth.currentUser!!.uid)
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
                val user = snapshot.toObject(User::class.java)
                uiState.value = uiState.value.copy(
                    user = user!!,
                    isLoading = false,
                    error = null
                )
            } else {
                Log.d(TAG, "User not found")
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "User not found"
                )
            }
        }
    }

    fun logout()
    {
        auth.signOut()
    }
}
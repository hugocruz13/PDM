package ipca.example.login_app.ui.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


data class LoginState(
    var email: String? = null,
    var password: String? = null,
    var error: String? = null,
    var loading: Boolean = false
)

class LoginViewModel : ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun updateEmail(email: String) {
        uiState.value = uiState.value.copy(email = email.trim())
    }

    fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }


    fun login(onLoginSuccess: () -> Unit) {
        uiState.value = uiState.value.copy(loading = true)

        if (uiState.value.email.isNullOrEmpty() || uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(error = "Email or password empty", loading = false)
            return
        }

        var auth: FirebaseAuth
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(uiState.value.email!!, uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    onLoginSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    uiState.value =
                        uiState.value.copy(error = "Authentication failed.", loading = false)
                }
            }
    }

    fun logout() {
        var auth: FirebaseAuth
        auth = Firebase.auth
        auth.signOut()
    }
}
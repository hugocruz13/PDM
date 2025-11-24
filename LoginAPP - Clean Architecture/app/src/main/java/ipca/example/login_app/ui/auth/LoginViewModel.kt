package ipca.example.login_app.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.login_app.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.launch



data class LoginState(
    var email: String? = null,
    var password: String? = null,
    var error: String? = null,
    var loading: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun updateEmail(email: String) {
        uiState.value = uiState.value.copy(email = email.trim())
    }

    fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }


    fun login(onLoginSuccess: () -> Unit) {
        uiState.value = uiState.value.copy(loading = true, error = null)

        val email = uiState.value.email
        val password = uiState.value.password

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Email or password empty",
                loading = false
            )
            return
        }

        uiState.value = uiState.value.copy(loading = true)

        viewModelScope.launch {
            userRepository.login(email, password).collect { result ->
                result.onSuccess {
                    uiState.value = uiState.value.copy(loading = false)
                    onLoginSuccess()
                }.onFailure { e ->
                    uiState.value = uiState.value.copy(
                        error = e.message,
                        loading = false
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout().collect { result ->
                result.onSuccess {
                    uiState.value = uiState.value.copy(loading = false)
                }.onFailure { e ->
                    uiState.value = uiState.value.copy(
                        error = e.message,
                        loading = false
                    )
                }
            }
        }
    }
}
package ipca.example.login_app.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.login_app.models.User
import ipca.example.login_app.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.launch


data class RegisterState(
    var email: String? = null,
    var password: String? = null,
    var name: String = "",
    var contact: Int? = null,
    var error: String? = null,
    var loading: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    var uiState = mutableStateOf(RegisterState())
        private set

    fun updateEmail(email: String) {
        uiState.value = uiState.value.copy(email = email.trim())
    }

    fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun updateName(name: String) {
        uiState.value = uiState.value.copy(name = name)
    }

    fun updateContact(contact: Int) {
        uiState.value = uiState.value.copy(contact = contact)
    }

    fun registar(onRegisterSuccess: () -> Unit) {
        uiState.value = uiState.value.copy(loading = true, error = null)

        val email = uiState.value.email
        val password = uiState.value.password

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(error = "Email ou password vazios", loading = false)
            return
        }

        uiState.value = uiState.value.copy(loading = true)

        viewModelScope.launch {
            userRepository.register(email, password).collect { result ->
                result.onSuccess {
                    userRepository.getUserId().collect { userIdResult ->
                        userIdResult.onSuccess { userId ->
                            val user = User(
                                userId,
                                uiState.value.name,
                                uiState.value.email,
                                uiState.value.contact
                            )
                            userRepository.createUser(user).collect { result ->
                                result.onSuccess {
                                    uiState.value = uiState.value.copy(loading = false)
                                }.onFailure { e ->
                                    uiState.value =
                                        uiState.value.copy(error = e.message, loading = false)
                                }

                            }
                        }.onFailure { e ->
                            uiState.value = uiState.value.copy(error = e.message, loading = false)
                        }
                    }
                    uiState.value = uiState.value.copy(loading = false)
                    onRegisterSuccess()
                }.onFailure { e ->
                    uiState.value = uiState.value.copy(error = e.message, loading = false)
                }
            }
        }
    }
}

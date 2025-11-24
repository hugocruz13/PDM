package ipca.example.login_app.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.login_app.models.User
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    private val TAG = "UserRepository"

    fun login(email: String, password: String): Flow<Result<FirebaseUser?>> = flow {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Result.success(result.user))
        } catch (e: Exception) {
            Log.d(TAG, "login failed: ${e.message}")
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    fun logout(): Flow<Result<FirebaseUser?>> = flow {
        try {
            auth.signOut()
            emit(Result.success(null))
        } catch (e: Exception) {
            Log.d(TAG, "logout failed: ${e.message}")
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)


    fun register(email: String, password: String): Flow<Result<FirebaseUser?>> = flow {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Result.success(result.user))
        } catch (e: Exception) {
            Log.d(TAG, "register failed: ${e.message}")
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)


    fun createUser(user: User): Flow<Result<Void?>> = flow {
        try {
            if (user.id.isNullOrEmpty()) {
                throw IllegalArgumentException("User ID cannot be null or empty")
            }
            val result = db.collection("users").document(user.id!!).set(user).await()
            emit(Result.success(result))
        } catch (e: Exception) {
            Log.d(TAG, "createUser failed: ${e.message}")
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)


    fun getUserId(): Flow<Result<String?>> = flow {
        try {
            val uid = auth.currentUser?.uid
            emit(Result.success(uid))
        } catch (e: Exception) {
            Log.d(TAG, "getUserId failed: ${e.message}")
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}

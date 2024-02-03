package ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.User
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.launch

class LoginScreenModel : StateScreenModel<LoginScreenModel.State>(State.Init) {
    private val auth: FirebaseAuth = Firebase.auth
    var email: MutableState<String> = mutableStateOf("jp87575@gmail.com")
    var password: MutableState<String> = mutableStateOf("")
    var errorMessage: MutableState<Pair<String, String>> = mutableStateOf(Pair("", ""))

    init {
        checkLoginState(auth.currentUser)
    }

    fun login() {
        screenModelScope.launch {
            mutableState.value = State.Loading

            val email = email.value
            val password = password.value

            when {
                email.isBlank() -> {
                    mutableState.value = State.Init
                    errorMessage.value = Pair("Invalid email address", "")
                    return@launch
                }

                password.isBlank() -> {
                    mutableState.value = State.Init
                    errorMessage.value = Pair("", "Invalid password")
                    return@launch
                }

                else -> {
                    try {
                        val authResult =
                            auth.signInWithEmailAndPassword(email = email, password = password)

                        checkLoginState(authResult.user)
                    } catch (ex: FirebaseAuthException) {
                        mutableState.value = State.Error(exception = ex)
                    }
                }
            }
        }
    }

    private fun checkLoginState(firebaseUser: FirebaseUser?) {
        firebaseUser?.let {
            mutableState.value = State.Success(firebaseUser = it)
            addUserToDatabase(it)
        } ?: run {
            mutableState.value =
                State.Error(exception = Throwable("Failed to login"))
        }
    }

    private fun addUserToDatabase(firebaseUser: FirebaseUser) {
        screenModelScope.launch {
            val db = Firebase.firestore
            val user = User(userId = firebaseUser.uid, name = firebaseUser.displayName ?: "User")
            println("firebase user id ${user.userId}")
            val userCollection = db.collection("Users")
            val documentSnapshot = userCollection.document(user.userId).get()
            if (!documentSnapshot.exists) {
                userCollection.document(user.userId).set(user)
            }
        }
    }

    private fun resetState() {
        errorMessage.value = Pair("", "")
        email.value = ""
        password.value = ""
        mutableState.value = State.Init
    }

    override fun onDispose() {
        super.onDispose()
        resetState()
    }

    sealed class State {
        data object Init : State()
        data object Loading : State()
        data class Success(val firebaseUser: FirebaseUser) : State()
        data class Error(val exception: Throwable) : State()
    }
}
package ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
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
        } ?: run {
            mutableState.value =
                State.Error(exception = Throwable("Failed to login"))
        }
    }

    fun resetState() {
        errorMessage.value = Pair("", "")
        email.value = ""
        password.value = ""
        mutableState.value = State.Init
    }

    sealed class State {
        data object Init : State()
        data object Loading : State()
        data class Success(val firebaseUser: FirebaseUser) : State()
        data class Error(val exception: Throwable) : State()
    }
}
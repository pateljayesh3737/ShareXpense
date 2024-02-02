package ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpScreenModel : StateScreenModel<SignUpScreenModel.State>(State.Init) {
    private val auth: FirebaseAuth = Firebase.auth
    var email: MutableState<String> = mutableStateOf("jp87575@gmail.com")
    var password: MutableState<String> = mutableStateOf("")
    var errorMessage: MutableState<Pair<String, String>> = mutableStateOf(Pair("", ""))

    init {
        screenModelScope.launch {
            auth.authStateChanged.collectLatest {
                println(it.toString())
            }
        }
    }

    fun createUserWithEmailAndPassword() {
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
                            auth.createUserWithEmailAndPassword(email = email, password = password)

                        mutableState.value = State.Success(authResult = authResult)
                    } catch (ex: FirebaseAuthException) {
                        mutableState.value = State.Error(exception = ex)
                    }
                }
            }
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
        data class Success(val authResult: AuthResult) : State()

        data class Error(val exception: FirebaseAuthException) : State()
    }
}
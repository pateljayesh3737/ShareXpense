package ui.group.expense

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import dev.gitlive.firebase.firestore.DocumentChange

class AddExpenseScreenModel : StateScreenModel<AddExpenseScreenModel.State>(State.Init) {
    var expenseDescription: MutableState<String> = mutableStateOf("")
    var cost: MutableState<Float> = mutableStateOf(0f)

    sealed class State {
        data object Init : State()
        data class Error(private val throwable: Throwable) : State()
        data class Success(private val documentChange: DocumentChange) : State()
        data object Loading : State()
    }
}
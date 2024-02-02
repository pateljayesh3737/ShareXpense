package ui.group.create

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Group
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.launch
import util.ROOT_GROUP_COLLECTION_NAME
import util.checkFirebaseUser
import util.uuid

class CreateGroupScreenModel : StateScreenModel<CreateGroupScreenModel.State>(State.Init) {

    private val db = Firebase.firestore
    var groupName: MutableState<String> = mutableStateOf("")

    init {
        Firebase.auth.currentUser.checkFirebaseUser {
            mutableState.value = State.Error(Throwable("Firebase user is not logged in"))
        }
    }

    fun createNewGroup() {
        screenModelScope.launch {
            mutableState.value = State.Loading

            if (groupName.value.isBlank()) {
                mutableState.value = State.Error(Throwable("Group name cannot be empty"))
                return@launch
            }

            val collectionReference = db.collection(ROOT_GROUP_COLLECTION_NAME)

            val group = Group(uuid(), groupName.value)
            collectionReference.document(group.groupId) // set groupId to document Id
                .set(Group.serializer(), group, encodeDefaults = true)

            mutableState.value = State.Success
        }
    }

    sealed class State {
        data object Init : State()
        data object Loading : State()
        data object Success : State()
        data class Error(val exception: Throwable) : State()
    }
}
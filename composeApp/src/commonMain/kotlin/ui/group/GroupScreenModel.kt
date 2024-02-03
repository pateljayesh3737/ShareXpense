package ui.group

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Group
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ROOT_GROUP_COLLECTION_NAME
import util.checkFirebaseUser

class GroupScreenModel : StateScreenModel<GroupScreenModel.State>(State.Init) {
    fun signOut() {
        screenModelScope.launch {
            Firebase.auth.signOut()
            mutableState.value = State.LoggedOut
        }
    }

    sealed class State {
        data object Init : State()
        data object Loading : State()
        data object LoggedOut : State()
        data class Success(val groups: MutableList<Group>) : State()
        data class Error(val exception: Throwable) : State()
    }

    private val db = Firebase.firestore

    init {
        if (Firebase.auth.currentUser.checkFirebaseUser {
                mutableState.value = State.Error(Throwable("Firebase user is not logged in"))
            }
        ) {
            screenModelScope.launch {
                val collectionReference = db.collection(ROOT_GROUP_COLLECTION_NAME)
                val groupSnapshots = collectionReference.snapshots
                groupSnapshots.collectLatest { qs ->
                    val groups = mutableListOf<Group>()
                    qs.documents.forEach { ds ->
                        val aGroup = ds.data<Group>()
                        groups.add(aGroup)
                    }
                    mutableState.value = State.Success(groups = groups)
                }
            }
        }
    }

}
package util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benasher44.uuid.uuid4
import data.User
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val ROOT_GROUP_COLLECTION_NAME = "Groups"
const val ROOT_USER_COLLECTION_NAME = "Users"

fun uuid(): String {
    return uuid4().toString()
}

fun FirebaseUser.addUserToDatabase(scope: CoroutineScope) {
    scope.launch {
        val db = Firebase.firestore
        val user = User(
            userId = uid,
            name = displayName ?: "User",
            email = email ?: ""
        )

        val userCollection = db.collection(collectionPath = ROOT_USER_COLLECTION_NAME)
        val documentSnapshot = userCollection.document(user.userId).get()
        if (!documentSnapshot.exists) {
            userCollection.document(user.userId).set(user)
        }
    }
}

fun FirebaseUser?.checkFirebaseUser(block: () -> Unit): Boolean =
    if (this == null) {
        block()
        false
    } else true

@Composable
fun CircularProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(50.dp)
            .padding(10.dp)
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar(
    onBackButtonClicked: () -> Unit,
    title: String,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = actions
    )
}
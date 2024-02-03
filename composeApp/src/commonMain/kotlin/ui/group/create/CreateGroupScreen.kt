package ui.group.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import util.CircularProgressBar
import util.MyTopAppBar

object CreateGroupScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val createGroupScreenModel = rememberScreenModel { CreateGroupScreenModel() }

        var groupName by remember { createGroupScreenModel.groupName }
        val createGroupState by createGroupScreenModel.state.collectAsState()

        Scaffold(topBar = {
            MyTopAppBar(onBackButtonClicked = { navigator.pop() }, title = "Create a Group")
        }) { paddingValues ->
            Column(
                Modifier.fillMaxWidth().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (val state = createGroupState) {
                    is CreateGroupScreenModel.State.Error -> {
                        println("Error: ${state.exception.message}")
                    }

                    is CreateGroupScreenModel.State.Init -> {
                        OutlinedTextField(
                            onValueChange = { groupName = it },
                            value = groupName,
                            placeholder = {
                                Text("Group Name")
                            },
                        )

                        Spacer(modifier = Modifier.padding(vertical = 10.dp))

                        OutlinedButton(onClick = { createGroupScreenModel.createNewGroup() }) {
                            Text("Create Group")
                        }
                    }

                    is CreateGroupScreenModel.State.Loading -> {
                        CircularProgressBar()
                    }

                    is CreateGroupScreenModel.State.Success -> {
                        navigator.pop()
                    }
                }
            }
        }
    }
}
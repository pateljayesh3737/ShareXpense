package ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.group.CreateGroupScreen
import ui.group.GroupList
import ui.group.GroupScreenModel
import util.CircularProgressBar

object HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val groupScreenModel = rememberScreenModel { GroupScreenModel() }

        val groupScreenState by groupScreenModel.state.collectAsState()

        Scaffold(topBar = { TopAppBar(title = { Text("ShareXpense") }) }) { paddingValues ->
            Column(
                Modifier.fillMaxWidth().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (val state = groupScreenState) {
                    is GroupScreenModel.State.Error -> {
                        println(state.exception.message)
                    }

                    is GroupScreenModel.State.Init -> {
                        Text("Create a new group to get started!")

                        Button(onClick = { navigator.push(CreateGroupScreen) }) {
                            Text("Create Group")
                        }
                    }

                    is GroupScreenModel.State.Loading -> {
                        CircularProgressBar()
                    }

                    is GroupScreenModel.State.Success -> {
                        GroupList(state.groups)

                        Spacer(modifier = Modifier.padding(vertical = 8.dp))

                        Button(onClick = { navigator.push(CreateGroupScreen) }) {
                            Text("Create Group")
                        }
                    }
                }
            }
        }
    }
}
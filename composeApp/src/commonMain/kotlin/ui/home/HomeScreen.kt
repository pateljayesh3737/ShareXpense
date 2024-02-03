package ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.group.GroupScreenModel
import ui.group.create.CreateGroupScreen
import ui.group.info.GroupInfoScreen
import ui.group.list.GroupList
import util.CircularProgressBar

object HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val groupScreenModel = rememberScreenModel { GroupScreenModel() }

        val groupScreenState by groupScreenModel.state.collectAsState()

        val createGroupLabel = "Create Group"

        Scaffold(
            topBar = { TopAppBar(title = { Text("ShareXpense") }) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        navigator.push(CreateGroupScreen)
                    },
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = createGroupLabel)
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(text = createGroupLabel)
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValues),
                horizontalAlignment = Alignment.Start
            ) {

                when (val state = groupScreenState) {
                    is GroupScreenModel.State.Error -> {
                        println(state.exception.message)
                    }

                    is GroupScreenModel.State.Init -> {
                        EmptyGroupListPlaceHolder(
                            modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally)
                        )
                    }

                    is GroupScreenModel.State.Loading -> {
                        CircularProgressBar()
                    }

                    is GroupScreenModel.State.Success -> {
                        val groups = state.groups
                        when {
                            groups.isEmpty() -> EmptyGroupListPlaceHolder(
                                modifier = Modifier.padding(10.dp)
                                    .align(Alignment.CenterHorizontally)
                            )

                            else -> GroupList(
                                groups = groups,
                                onGroupSelected = {
                                    navigator.push(GroupInfoScreen(group = it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun EmptyGroupListPlaceHolder(modifier: Modifier) {
        Text(
            text = "Create a new group to get started!", modifier = modifier,
            style = MaterialTheme.typography.titleLarge.copy(
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal
            )
        )
    }
}
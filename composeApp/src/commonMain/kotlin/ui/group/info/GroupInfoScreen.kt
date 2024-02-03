package ui.group.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.Group
import data.SharedExpense
import ui.group.expense.AddExpenseScreen
import util.MyTopAppBar

data class GroupInfoScreen(val group: Group) : Screen {

    private val addExpenseLabel = "Add expense"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                MyTopAppBar(onBackButtonClicked = { navigator.pop() }, title = group.name,)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = { navigator.push(AddExpenseScreen(group)) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = addExpenseLabel)
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(text = addExpenseLabel)
                }
            }
        ) { paddingValues ->
            Column(
                Modifier.fillMaxWidth().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExpenseList(
                    sharedExpenses = group.sharedExpenses,
                    onExpenseSelected = {

                    }
                )
            }
        }
    }

    @Composable
    private fun ExpenseList(
        sharedExpenses: List<SharedExpense>,
        onExpenseSelected: (SharedExpense) -> Unit
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(items = sharedExpenses) { sharedExpense ->
                ExpenseCard(sharedExpense = sharedExpense, onExpenseSelected = onExpenseSelected)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ExpenseCard(
        sharedExpense: SharedExpense,
        onExpenseSelected: (SharedExpense) -> Unit
    ) {
        OutlinedCard(onClick = { onExpenseSelected(sharedExpense) }) {
            Text(text = group.name, modifier = Modifier.fillMaxWidth().padding(all = 10.dp))
        }
    }
}
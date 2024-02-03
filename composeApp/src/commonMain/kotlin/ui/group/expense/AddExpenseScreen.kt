package ui.group.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.Group
import kotlinx.serialization.Serializable
import util.MyTopAppBar

@Serializable
data class AddExpenseScreen(private val group: Group) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val addExpenseScreenModel = rememberScreenModel { AddExpenseScreenModel() }

        var expenseDescription by remember { addExpenseScreenModel.expenseDescription }
        var cost by remember { addExpenseScreenModel.cost }

        Scaffold(
            topBar = {
                MyTopAppBar(
                    onBackButtonClicked = { navigator.pop() },
                    title = "Add expense",
                    actions = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Done")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "With you and: ${group.name}")

                    Spacer(Modifier.padding(top = 10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        OutlinedTextField(
                            value = expenseDescription,
                            onValueChange = { expenseDescription = it },
                            placeholder = { Text("Enter a description") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        OutlinedTextField(
                            value = "$cost",
                            onValueChange = {
                                val cleanedInput = it.replace(Regex("[^\\d.]"), "")
                                val parts = cleanedInput.split(".")

                                if (parts.size <= 2) {
                                    cost = cleanedInput.toFloatOrNull() ?: 0f
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier.padding(all = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Paid by")
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        OutlinedButton(onClick = {}) {
                            Text("you")
                        }
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))


                        Text(text = "and split")
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        OutlinedButton(onClick = {}) {
                            Text("equally")
                        }
                    }
                }
            }
        }
    }
}

package ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.home.HomeScreen

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { LoginScreenModel() }

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var email by remember { screenModel.email }
                var password by remember { screenModel.password }

                val loginState by screenModel.state.collectAsState()
                val errorMessageState by remember { screenModel.errorMessage }

                when (val state = loginState) {
                    is LoginScreenModel.State.Error -> {
                        println("Error found while logging in")
                    }

                    is LoginScreenModel.State.Init -> {
                        Text(
                            "Welcome to ShareXpense!",
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(Modifier.padding(vertical = 16.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            supportingText = {
                                errorMessageState.first.let {
                                    if (it.isNotEmpty()) Text(it)
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            isError = errorMessageState.first.isNotEmpty()
                        )
                        Spacer(Modifier.padding(vertical = 10.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            supportingText = {
                                errorMessageState.second.let {
                                    if (it.isNotEmpty()) Text(it)
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            isError = errorMessageState.second.isNotEmpty(),
                        )
                        Spacer(Modifier.padding(vertical = 10.dp))

                        ElevatedButton(onClick = { screenModel.login() }) {
                            Text("Login", modifier = Modifier.padding(horizontal = 16.dp))
                        }

                        Spacer(Modifier.padding(vertical = 10.dp))

                        Text(
                            "Don't have account? Sign up",
                            Modifier.clickable { navigator.push(SignUpScreen) }.padding(all = 8.dp)
                        )

                    }

                    is LoginScreenModel.State.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    is LoginScreenModel.State.Success -> {
                        val firebaseUser = state.firebaseUser
                        println("Success : $firebaseUser")
                        navigator.push(HomeScreen)
                    }
                }
            }
        }
    }

}
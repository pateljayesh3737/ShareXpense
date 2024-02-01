package ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.home.HomeScreen

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Welcome to ShareXpense!", style = MaterialTheme.typography.displaySmall)
                Spacer(Modifier.padding(vertical = 16.dp))

                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Username") })
                Spacer(Modifier.padding(vertical = 10.dp))

                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Password") })
                Spacer(Modifier.padding(vertical = 10.dp))

                ElevatedButton(onClick = { navigator.push(HomeScreen) }) {
                    Text("Login", modifier = Modifier.padding(horizontal = 16.dp))
                }

                Spacer(Modifier.padding(vertical = 10.dp))

                Text("Don't have account? Sign up", Modifier.clickable { navigator.push(SignUpScreen) }.padding(all = 8.dp))

            }
        }
    }


}
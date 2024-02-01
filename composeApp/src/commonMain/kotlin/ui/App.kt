package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.login.LoginScreen
import ui.theme.ShareXpenseTheme

@Composable
fun App() {
    ShareXpenseTheme {
        Navigator(screen = LoginScreen)
    }
}
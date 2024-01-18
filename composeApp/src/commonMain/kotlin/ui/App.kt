package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.home.HomeScreen
import ui.theme.ShareXpenseTheme

@Composable
fun App() {
    ShareXpenseTheme {
        Navigator(screen = HomeScreen)
    }
}
package ui.group

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

object GroupScreen : Screen {

    @Composable
    override fun Content() {
        Surface(Modifier.fillMaxSize()) {
            Text("Group 1")

        }
    }
}
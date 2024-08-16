import androidx.compose.material3.*
import androidx.compose.runtime.*
import core.theme.AppTheme
import ui.main.MainScreen

@Composable
fun App() {
    AppTheme {
        Surface {
            MainScreen()
        }
    }
}
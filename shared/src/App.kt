import androidx.compose.material3.*
import androidx.compose.runtime.*
import core.theme.AppTheme
import org.koin.compose.KoinContext
import ui.main.MainScreen

@Composable
fun App() {
    AppTheme {
        KoinContext {
            Surface {
                MainScreen()
            }
        }
    }
}
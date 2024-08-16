import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

fun main() = application {
    var isMainWindowVisible by remember { mutableStateOf(true) }
    var isTrayWindowVisible by remember { mutableStateOf(false) }

    Tray(
        icon = rememberVectorPainter(Icons.Default.Build),
        onAction = {
            isTrayWindowVisible = true
        },
        menu = {
            Item("Open") {
                isMainWindowVisible = true
            }
            Item("Exit") {
                exitApplication()
            }
        }
    )
    
    Window(
        state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition.Aligned(Alignment.BottomEnd),
            size = DpSize(
                width = 450.dp,
                height = 700.dp
            )
        ),
        visible = isTrayWindowVisible,
        onCloseRequest = {
            isTrayWindowVisible = false
        },
        undecorated = true,
        resizable = false,
        alwaysOnTop = true,
        title = "PomodorTimer Tray"
    ) {
        LaunchedEffect(Unit) {
            this@Window.window.addWindowFocusListener(object: WindowFocusListener {
                override fun windowGainedFocus(e: WindowEvent?) = Unit

                override fun windowLostFocus(e: WindowEvent?) {
                    isTrayWindowVisible = false
                }
            })
        }
        
        App()
    }
    
    // Main Window
    Window(
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(
                width = 700.dp,
                height = 700.dp
            )
        ),
        visible = isMainWindowVisible,
        onCloseRequest = {
            isMainWindowVisible = false
        },
        title = "PomodoreTimer",
    ) {
        App()
    }
}
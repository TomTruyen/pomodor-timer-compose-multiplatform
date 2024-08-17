import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import components.ShapedWindow
import di.initKoin
import utils.onFocusLost
import java.awt.Dimension

val MIN_WIDTH = 450.dp
val MIN_HEIGHT = 700.dp

private val koin = initKoin().koin

fun main() = application {
    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Desktop(
            showPushNotification = true,
//            notificationIconPath = "TO BE DETERMINED"
        )
    )
    
    
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
                width = MIN_WIDTH,
                height = MIN_HEIGHT
            )
        ),
        visible = isTrayWindowVisible,
        onCloseRequest = {
            isTrayWindowVisible = false
        },
        transparent = true,
        undecorated = true,
        resizable = false,
        alwaysOnTop = true,
    ) {
        onFocusLost {
            isTrayWindowVisible = false
        }

        WindowDraggableArea {
            ShapedWindow(
                shape = RoundedCornerShape(8.dp)
            ) {
                App()
            }
        }
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
        window.minimumSize = Dimension(MIN_WIDTH.value.toInt(), MIN_HEIGHT.value.toInt())
        
        App()
    }
}
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import components.ShapedWindow
import core.icons.generated.PinFilled
import core.icons.generated.PinOutlined
import di.initKoin
import di.jvmModule
import org.koin.core.context.loadKoinModules
import utils.TrayPositionProvider
import utils.onFocusLost
import java.awt.Dimension

val MIN_WIDTH = 450.dp
val MIN_HEIGHT = 700.dp

private val koin = initKoin {
    loadKoinModules(jvmModule)
}.koin

fun main() = application {
    val trayPositionProvider: TrayPositionProvider by koin.inject<TrayPositionProvider>()

    val appIcon = remember {
        BitmapPainter(
            image = useResource("drawable/icon.png", ::loadImageBitmap)
        )
    }

    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Desktop(
            showPushNotification = true,
//            notificationIconPath = "TO BE DETERMINED"
        )
    )
    
    
    var isMainWindowVisible by remember { mutableStateOf(true) }
    var isTrayWindowVisible by remember { mutableStateOf(false) }
    var isTrayWindowPinned by remember { mutableStateOf(false) }

    Tray(
        icon = appIcon,
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
        icon = appIcon,
        state = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition.Aligned(
                alignment = trayPositionProvider.calculateTrayPosition()
            ),
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
            if(!isTrayWindowPinned) {
                isTrayWindowVisible = false
            }
        }

        WindowDraggableArea {
            ShapedWindow(
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    App()

                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {
                            isTrayWindowPinned = !isTrayWindowPinned
                        },
                    ) {
                        Icon(
                            imageVector = if(isTrayWindowPinned) PinFilled else PinOutlined,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
    
    // Main Window
    Window(
        icon = appIcon,
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
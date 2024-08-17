import androidx.compose.ui.window.ComposeUIViewController
import App
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

fun ViewController() = ComposeUIViewController {
    // TODO: Test Notifications. Might have to be initialized at another place
    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Ios(
            showPushNotification = true,
            askNotificationPermissionOnStart = true,
//            notificationSoundName = "TO BE DETERMINED"
        )
    )

    App()
}
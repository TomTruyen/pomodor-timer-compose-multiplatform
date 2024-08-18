package models

import com.mmk.kmpnotifier.notification.NotifierManager
import core.models.NotificationMessage

class NotificationManager() {
    private val notifier = NotifierManager.getLocalNotifier()
    
    fun sendNotification(message: NotificationMessage) {
        notifier.notify(
            title = message.title,
            body = message.body,
        )
    }
}
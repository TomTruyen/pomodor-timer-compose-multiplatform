package core.models

import kotlinx.datetime.Clock
import kotlin.random.Random

sealed class TimeType {
    private val random = Random(Clock.System.now().toEpochMilliseconds())
    
    protected abstract val messages: List<NotificationMessage>
    
    fun getNotificationMessage() = messages[random.nextInt(messages.size)]

    data object Break: TimeType() {
        override val messages = listOf(
            NotificationMessage(title = "Back to Business!", body = "Break time is over. Let's get back to work!"),
            NotificationMessage(title = "Focus Mode: Activated", body = "Your break is over. Time to dive back in."),
            NotificationMessage(title = "Ready to Conquer?", body = "Break time is done. Let's crush those tasks!")
        )
    }

    data object Focus: TimeType() {
        override val messages = listOf(
            NotificationMessage(title = "Time's Up!", body = "Focus session complete. Time to recharge!"),
            NotificationMessage(title = "Brain Break Ahead!", body = "Your focus time is over. Enjoy a well-deserved break."),
            NotificationMessage(title = "Let's take a breather!", body = "Focus session finished. It's time to relax and refocus."),
        )
    }
}
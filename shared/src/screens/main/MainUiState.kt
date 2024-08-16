package screens.main

import extensions.asFormattedDuration
import models.CountDownTimerState

data class MainUiState(
    val totalTime: Int = 0,
    val remainingTime: Int = 0,
    val timerState: CountDownTimerState = CountDownTimerState.IDLE,
) {
    val formattedRemainingTime: String
        get() = remainingTime.asFormattedDuration()
}

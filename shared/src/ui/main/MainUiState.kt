package ui.main

import models.CountDownTimerState

data class MainUiState(
    val totalTime: Int = 0,
    val remainingTime: Int = 0,
    val timerState: CountDownTimerState = CountDownTimerState.IDLE,
)
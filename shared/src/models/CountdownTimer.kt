package models

expect class CountDownTimer(
    intervalMillis: Long = 1_000L,
    listener: CountDownTimerListener,
) {
    fun start(initialMillis: Long)
    fun cancel()
}

interface CountDownTimerListener {
    fun onTick(millisUntilFinished: Long)
    fun onStateChange(state: CountDownTimerState)
}

enum class CountDownTimerState {
        IDLE,
        RUNNING,
        FINISHED,
    }
package models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

/**
 * @param template to generate the sequence for the [CountDownTimer]
 */
class PomodoreTimer(template: Template = Template.Default) {
    private val sequence = template.generateSequence()

    private val _state = MutableStateFlow(CountDownTimerState.IDLE)
    val state = _state.asStateFlow()

    private val _remainingTime = MutableStateFlow(
        value = sequence.current()?.data?.timeInSeconds ?: 0
    )
    val remainingTime = _remainingTime.asStateFlow()

    val totalTime: Int
        get() = sequence.current()?.data?.timeInSeconds ?: 0

    private val timer by lazy {
        CountDownTimer(
            intervalMillis = TICK_INTERVAL,
            listener = object: CountDownTimerListener {
                override fun onTick(millisUntilFinished: Long) {
                    _remainingTime.update {
                        (millisUntilFinished / 1000).toInt().coerceAtLeast(0)
                    }
                }

                override fun onStateChange(state: CountDownTimerState) {
                    _state.update { state }

                    if(state == CountDownTimerState.FINISHED) {
                        println("Finish Trigger")
                        onFinish()
                    }
                }
            }
        )
    }

    fun start() = timer.start(_remainingTime.value * 1000L)

    fun pause() = timer.cancel()

    fun reset() {
        timer.cancel()

        val node = sequence.reset()

        _remainingTime.update {
            node?.data?.timeInSeconds ?: 0
        }
    }

    private fun onFinish() {
        val (entry) = sequence.next()
            ?: sequence.reset()
            ?: return

        val timeInSeconds = _remainingTime.updateAndGet { entry.timeInSeconds }

        if(entry.autoStart) {
            timer.start(timeInSeconds * 1000L)
        }
    }

    companion object {
        private const val TICK_INTERVAL = 1_000L
    }
}
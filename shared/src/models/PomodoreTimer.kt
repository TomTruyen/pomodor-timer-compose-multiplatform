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

    private val _state = MutableStateFlow(
        State(
            totalTime = sequence.current()?.data?.timeInSeconds ?: 0,
            remainingTime = sequence.current()?.data?.timeInSeconds ?: 0,
            countDownTimerState = CountDownTimerState.IDLE,
        )
    )

    val state = _state.asStateFlow()

    private val timer by lazy {
        CountDownTimer(
            intervalMillis = TICK_INTERVAL,
            listener = object: CountDownTimerListener {
                override fun onTick(millisUntilFinished: Long) {
                    setState {
                        copy(
                            remainingTime = (millisUntilFinished / 1000).toInt().coerceAtLeast(0)
                        )
                    }
                }

                override fun onStateChange(state: CountDownTimerState) {
                    setState {
                        copy(
                            countDownTimerState = state
                        )
                    }

                    if(state == CountDownTimerState.FINISHED) {
                        println("Finish Trigger")
                        onFinish()
                    }
                }
            }
        )
    }

    fun start() = timer.start(state.value.remainingTime * 1000L)

    fun pause() = timer.cancel()

    fun reset() {
        timer.cancel()

        val node = sequence.reset()

        setState {
            copy(
                totalTime = node?.data?.timeInSeconds ?: 0,
                remainingTime = node?.data?.timeInSeconds ?: 0,
                countDownTimerState = CountDownTimerState.IDLE,
            )
        }
    }

    private fun onFinish() {
        val (entry) = sequence.next()
            ?: sequence.reset()
            ?: return

        val (_, remainingTime) = setState {
            copy(
                remainingTime = entry.timeInSeconds,
                totalTime = entry.timeInSeconds,
            )
        }

        if(entry.autoStart) {
            timer.start(remainingTime * 1000L)
        }
    }

    private fun setState(block: State.() -> State) = _state.updateAndGet(block)

    data class State(
        val totalTime: Int = 0,
        val remainingTime: Int = 0,
        val countDownTimerState: CountDownTimerState = CountDownTimerState.IDLE,
    )

    companion object {
        private const val TICK_INTERVAL = 1_000L
    }
}
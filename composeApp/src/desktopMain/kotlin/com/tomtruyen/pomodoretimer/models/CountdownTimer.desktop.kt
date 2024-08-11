import java.util.Timer
import kotlin.concurrent.fixedRateTimer

actual class CountDownTimer actual constructor(
    private val intervalMillis: Long,
    private val listener: CountDownTimerListener
) {
    private var timer: Timer? = null
    private var millisUntilFinished: Long = 0L

    actual fun start(initialMillis: Long) {
        timer?.cancel()
        
        millisUntilFinished = initialMillis
        
        timer = fixedRateTimer(initialDelay = 0, period = intervalMillis) {
            if (millisUntilFinished <= 0) {
                timer?.cancel()
                timer = null
                
                listener.onStateChange(CountDownTimerState.FINISHED)
            } else {
                listener.onTick(millisUntilFinished)
                
                millisUntilFinished -= intervalMillis
            }
        }
        
        listener.onStateChange(CountDownTimerState.RUNNING)
    }

    actual fun cancel() {
        timer?.cancel()
        timer = null
        
        listener.onStateChange(CountDownTimerState.IDLE)
    }
}
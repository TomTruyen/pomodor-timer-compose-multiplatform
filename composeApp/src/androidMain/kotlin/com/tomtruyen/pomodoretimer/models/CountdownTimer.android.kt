actual class CountDownTimer actual constructor(
    private val intervalMillis: Long,
    private val listener: CountDownTimerListener,
) {
    private var timer: android.os.CountDownTimer? = null
    
    actual fun start(initialMillis: Long) {
        timer?.cancel()
        
        timer = object : android.os.CountDownTimer(initialMillis, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                listener.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                timer?.cancel()
                timer = null
                
                listener.onStateChange(CountDownTimerState.FINISHED)
            }
        }.start()
        
        listener.onStateChange(CountDownTimerState.RUNNING)
    }

    actual fun cancel() {
        timer?.cancel()
        timer = null
        
        listener.onStateChange(CountDownTimerState.IDLE)
    }
}
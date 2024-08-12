package models

actual class CountDownTimer actual constructor(
    private val intervalMillis: Long,
    private val listener: CountDownTimerListener
) {
    private var timer: NSTimer? = null
    private var endTimeMillis: Long = 0L

    @ObjCAction
    fun onTimerTick(timer: NSTimer) {
        val currentTimeMillis = (NSDate().timeIntervalSince1970 * 1000).toLong()
        val millisUntilFinished = endTimeMillis - currentTimeMillis

        if (millisUntilFinished <= 0) {
            onTimerFinish(timer)
        } else {
            listener.onTick(millisUntilFinished)
        }
    }

    @ObjCAction
    fun onTimerFinish(timer: NSTimer) {
        this.timer?.invalidate()
        this.timer = null
        listener.onStateChange(CountDownTimerState.FINISHED)
    }

    actual fun start(initialMillis: Long) {
        endTimeMillis = (NSDate().timeIntervalSince1970 * 1000).toLong() + initialMillis
        
        timer?.invalidate() 
        timer = NSTimer.scheduledTimerWithTimeInterval(
            interval = intervalMillis / 1000.0,
            repeats = true
        ) { timer ->
            onTimerTick(timer)
        }
        
        listener.onStateChange(CountDownTimerState.RUNNING)
    }

    actual fun cancel() {
        timer?.invalidate()
        timer = null
        
        listener.onStateChange(CountDownTimerState.IDLE)
    }
}
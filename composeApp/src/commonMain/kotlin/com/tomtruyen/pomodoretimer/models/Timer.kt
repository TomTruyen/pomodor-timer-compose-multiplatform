package com.tomtruyen.pomodoretimer.models

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * @param sequence LinkedList with entries to loop through
 * @param scope [CoroutineScope] to use for the timer loop
 */
open class Timer(
    private val sequence: LinkedList<TimeEntry>,
    private val scope: CoroutineScope,
) {
    private val _state = MutableStateFlow(State.IDLE)
    val state = _state.asStateFlow()
    
    private val _remainingTime = MutableStateFlow(
        value = sequence.current()?.data?.time ?: 0
    )
    val remainingTime = _remainingTime.asStateFlow()
    
    private var job: Job? = null
    
    /**
     * Starts the timer
     */
    fun start() {
        job?.cancel()
        job = scope.launch {
            while(true) {
                _state.update { State.RUNNING }
                
                delay(TICK_INTERVAL)
                
                val remaining = _remainingTime.updateAndGet { remaining ->
                    (remaining - (TICK_INTERVAL / 1000)).toInt().coerceAtLeast(0)
                }
                
                val isFinished = remaining == 0
                if(isFinished) {
                    _state.update { State.FINISHED }
                    
                    // Get next sequence entry or reset the loop
                    val (entry) = sequence.next()
                            ?: sequence.reset()
                            ?: break
                    
                    _remainingTime.update { entry.time }
                    
                    if(!entry.autoStart) break
                }
            }
        }
    }
    
    /**
     * Pauses the timer
     */
    fun pause() {
        job?.cancel()
        
        _state.update { State.PAUSED }
    }
    
    /**
     * Resets the timer to the intial state
     */
    fun reset() {
        job?.cancel()
        
        _state.update { State.IDLE }
        
        val node = sequence.reset()
        _remainingTime.update {
            node?.data?.time ?: 0
        }
    }
    
    enum class State {
        IDLE,
        RUNNING,
        PAUSED,
        FINISHED,
    }
    
    companion object {
        private const val TICK_INTERVAL = 1000L
    }
}
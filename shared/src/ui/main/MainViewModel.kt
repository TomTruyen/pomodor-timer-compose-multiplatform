package ui.main

import androidx.lifecycle.viewModelScope
import core.BaseViewModel
import kotlinx.coroutines.launch
import models.PomodoreTimer

class MainViewModel(
    private val timer: PomodoreTimer
): BaseViewModel<MainUiState, MainUiAction>(
    initialState = MainUiState()
) {
    init {
        observeTimerState()
    }
    
    private fun observeTimerState() = viewModelScope.launch {
        timer.state.collect { state ->
            setState {
                copy(
                    totalTime = state.totalTime,
                    remainingTime = state.remainingTime,
                    timerState = state.countDownTimerState
                )
            }
        }
    }
    
    override fun onAction(action: MainUiAction) {
        when(action) {
            MainUiAction.StartTimer -> timer.start()
            MainUiAction.PauseTimer -> timer.pause()
            MainUiAction.ResetTimer -> timer.reset()
        }
    }
}
package ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import components.CountdownProgressTimer
import waveprogress.WaveProgress
import models.CountDownTimerState
import models.PomodoreTimer

// TODO: Fix - Font face (question asked: https://kotlinlang.slack.com/archives/C062WG3A7T8/p1723735507094659)

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel {
        MainViewModel(
            timer = PomodoreTimer()
        )
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    MainScreenContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MainScreenContent(
    uiState: MainUiState,
    onAction: (MainUiAction) -> Unit,
) {
    val progress by remember(uiState.remainingTime, uiState.totalTime) {
        mutableStateOf(
            value = uiState.remainingTime.toFloat() / uiState.totalTime.toFloat()
        )
    }
    
    WaveProgress(
        progress = progress,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(64.dp)
            ) {
                CountdownProgressTimer(
                    remainingTime = uiState.remainingTime,
                )
                
                // TODO: Replace with Button like in https://dribbble.com/shots/2492858-Liquid-Pomodoro-Timer
                Button(
                    onClick = {
                        if(uiState.timerState == CountDownTimerState.RUNNING) {
                            onAction(MainUiAction.PauseTimer)
                        } else {
                            onAction(MainUiAction.StartTimer)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if(uiState.timerState == CountDownTimerState.RUNNING) Icons.Default.Star else Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                }
            }
             
        }
    )
}
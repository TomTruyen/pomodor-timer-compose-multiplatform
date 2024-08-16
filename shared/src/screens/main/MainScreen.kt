package screens.main

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.CountdownProgressTimer
import models.CountDownTimerState
import androidx.lifecycle.viewmodel.compose.viewModel
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.normal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CountdownProgressTimer(remainingTime = uiState.remainingTime)

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
        
        // Buttons
        Button(
            onClick = {
                onAction(MainUiAction.ResetTimer)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )
        }
    }
}
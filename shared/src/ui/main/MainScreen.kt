package ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import circlebutton.CircleButton
import components.CountdownProgressTimer
import core.icons.generated.Pause
import core.icons.generated.Play
import core.icons.generated.Stop
import waveprogress.WaveProgress
import models.CountDownTimerState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

// TODO: Fix - Font face (question asked: https://kotlinlang.slack.com/archives/C062WG3A7T8/p1723735507094659)

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel()
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
            value = 1f - uiState.remainingTime.toFloat() / uiState.totalTime.toFloat()
        )
    }

    var contentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    
    val isTimerRunning by remember {
        derivedStateOf {
            uiState.timerState == CountDownTimerState.RUNNING
        }
    }

    WaveProgress(
        progress = progress,
        contentSize = contentSize,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(64.dp),
                modifier = Modifier.onSizeChanged {
                    contentSize = it
                },
            ) {
                CountdownProgressTimer(
                    remainingTime = uiState.remainingTime,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize * 1.5,
                    )
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
                ) {
                    CircleButton(
                        modifier = Modifier.size(40.dp),
                        icon = Stop,
                        iconSize = 16.dp,
                        onClick = {
                            onAction(MainUiAction.ResetTimer)
                        }
                    )

                    CircleButton(
                        modifier = Modifier.size(64.dp),
                        icon = if(isTimerRunning) Pause else Play,
                        iconSize = 24.dp,
                        onClick = {
                            if(isTimerRunning) {
                                onAction(MainUiAction.PauseTimer)
                            } else {
                                onAction(MainUiAction.StartTimer)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }
            }
        }
    )
}
package com.tomtruyen.pomodoretimer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tomtruyen.pomodoretimer.extensions.asFormattedDuration
import com.tomtruyen.pomodoretimer.models.PomodoreTimer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        TimerTest()
    }
}

// TODO: Remove. This is just for testing the basic timer
@Composable
private fun TimerTest() {
    val timer = remember {
        // TODO: Important!!! This should be a Singleton.
        //  Reasoning: We might sometimes initialize the composable that contains this twice.
        //  Example: The Main Desktop Window and the Tray Window. They both have their own instance of App Composable and currently also of the Timer
        //  They should share the same instance. 
        PomodoreTimer()
    }

    val remainingTime by timer.remainingTime.collectAsState()
    val state by timer.state.collectAsState()

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.normal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Tick: ${remainingTime.asFormattedDuration()}")

            // Buttons
            Button(
                onClick = {
                    if(state == CountDownTimerState.RUNNING) {
                        timer.pause()
                    } else {
                        timer.start()
                    }
                }
            ) {
                Icon(
                    imageVector = if(state == CountDownTimerState.RUNNING) Icons.Default.Star else Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
        }
}
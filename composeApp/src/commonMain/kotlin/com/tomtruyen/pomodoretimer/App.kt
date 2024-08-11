package com.tomtruyen.pomodoretimer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomtruyen.pomodoretimer.extensions.asFormattedDuration
import com.tomtruyen.pomodoretimer.models.PomodoreTimer
import com.tomtruyen.pomodoretimer.models.Template
import com.tomtruyen.pomodoretimer.models.Timer
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
        PomodoreTimer()
    }

    val remainingTime by timer.remainingTime.collectAsState()
    val state by timer.state.collectAsState()

    LaunchedEffect(remainingTime) {
        println("New Time: $remainingTime")
    }

    LaunchedEffect(state) {
        println("New State: $state")
    }

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
                    if(timer.state.value == Timer.State.RUNNING) {
                        timer.pause()
                    } else {
                        timer.start()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
        }
}
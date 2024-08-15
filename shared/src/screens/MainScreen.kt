package screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.CountdownProgressTimer
import models.CountDownTimerState
import models.PomodoreTimer

@Composable
fun MainScreen() {
    // TODO: ViewModel!
    val timer = remember {
        // TODO: Important!!! This should be a Singleton. -> KOIN!
        //  Reasoning: We might sometimes initialize the composable that contains this twice.
        //  Example: The Main Desktop Window and the Tray Window. They both have their own instance of App Composable and currently also of the Timer
        //  They should share the same instance.
        PomodoreTimer()
    }

    val state by timer.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.normal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CountdownProgressTimer(
            timer = timer,
            size = 300.dp,
        )

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

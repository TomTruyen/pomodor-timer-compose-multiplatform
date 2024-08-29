package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import animatedcounter.AnimatedCounter
import core.extensions.hours
import core.extensions.minutes
import core.extensions.seconds

@Composable
fun CountdownProgressTimer(
    remainingTime: Int,
    style: TextStyle = MaterialTheme.typography.displayLarge,
    modifier: Modifier = Modifier
) {
    val hours by remember(remainingTime) {
        mutableIntStateOf(remainingTime.hours())
    }
    
    val minutes by remember(remainingTime) {
        mutableIntStateOf(remainingTime.minutes())
    }

    val seconds by remember(remainingTime) {
        mutableIntStateOf(remainingTime.seconds())
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = hours > 0
        ) {
            AnimatedCounter(
                count = hours,
                style = style,
            )

            Text(
                text = ":",
                style = style
            )
        }

        AnimatedCounter(
            count = minutes,
            style = style,
        )
        
        Text(
            text = ":",
            style = style
        )

        AnimatedCounter(
            count = seconds,
            style = style,
        )
    }
}
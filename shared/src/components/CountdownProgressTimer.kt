package components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.asFormattedDuration
import models.PomodoreTimer
import pomodor_timer_compose_multiplatform.shared.generated.resources.Res

@Composable
fun CountdownProgressTimer(
    timer: PomodoreTimer,
    size: Dp,
    stroke: Dp = 8.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    val remainingTime by timer.remainingTime.collectAsState()
    
    val progress by remember {
        derivedStateOf {
            val newValue = remainingTime / timer.totalTime.toFloat()
            
            println(newValue)
            
            newValue
        }
    }
    
    val progressAnimation by animateFloatAsState(
        targetValue = progress
    )
    
    Box(
        modifier = modifier.size(
            size = size
        ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val radius = size.toPx() / 2
            val strokeWidth = stroke.toPx()
            
            drawCircle(
                color = backgroundColor,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = -360 * progressAnimation,
                useCenter = false,
                size = Size(
                    width = radius * 2,
                    height = radius * 2
                ),
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }
        
        Text(
            text = remainingTime.asFormattedDuration(),
            style = MaterialTheme.typography.displayMedium,
        )
    }
}
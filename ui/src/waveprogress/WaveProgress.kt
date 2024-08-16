package waveprogress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import core.theme.LightGreen
import core.theme.OffWhite
import kotlin.random.Random

// TODO: Fix the idle state. Waves should still move on the spot and not go flat
// TODO: Fix colors being wrong. It is filling the entire background with MintGreen though it should be offwhite

@Composable
fun WaveProgress(
    progress: Float,
    content: @Composable () -> Unit,
) = BoxWithConstraints {
    val density = LocalDensity.current
    val height = with(density) { maxHeight.roundToPx() }

    val aYs = calculateYs(height = height, waterLevel = progress, intensityMultiplier = .4f)
    val aYs2 = calculateYs(height = height, waterLevel = progress, intensityMultiplier = .5f)
    val aYs3 = calculateYs(height = height, waterLevel = progress, intensityMultiplier = .7f)

    val currentY = height * progress
    val animatedY by animateFloatAsState(
        targetValue = height * progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawPath(
                    path = ayPath(
                        aYs, size, currentY, animatedY
                    ),
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            OffWhite,
                            Color.White,
                        )
                    )
                )

                drawPath(
                    path = ayPath(
                        aYs2, size, currentY, animatedY
                    ),
                    alpha = .5f,
                    color = LightGreen,
                )

                drawPath(
                    path = ayPath(
                        aYs3, size, currentY, animatedY
                    ),
                    alpha = .3f,
                    color = LightGreen,
                )
            }
        )
        
        content()
    }
}

fun ayPath(aYs: List<Int>, size: Size, currentY: Float, animatedY: Float): Path {
    return Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, animatedY)
        val interval = size.width * (1 / (aYs.size + 1).toFloat())
        aYs.forEachIndexed { index, y ->
            val segmentIndex = (index + 1) / (aYs.size + 1).toFloat()
            val x = size.width * segmentIndex
            cubicTo(
                x1 = if (index == 0) 0f else x - interval / 2f,
                y1 = aYs.getOrNull(index - 1)?.toFloat() ?: currentY,
                x2 = x - interval / 2f,
                y2 = y.toFloat(),
                x3 = x,
                y3 = y.toFloat(),
            )
        }

        cubicTo(
            x1 = size.width - interval / 2f,
            y1 = aYs.last().toFloat(),
            x2 = size.width,
            y2 = animatedY,
            x3 = size.width,
            y3 = animatedY,
        )
        lineTo(size.width, 0f)
        close()
    }
}

@Composable
fun calculateYs(height: Int, waterLevel: Float, intensityMultiplier: Float): List<Int> {
    val total = 6
    return (0..total).map {
        calculateY(height = height, waterLevel = waterLevel,
            ((if (it > total / 2f) total - it else it) / (total / 2f) * 1f) *
                intensityMultiplier
        )
    }.toList()
}

@Composable
fun calculateY(height: Int, waterLevel: Float, intensity: Float): Int {
    val density = LocalDensity.current
    var y1 by remember {
        mutableStateOf(0)
    }

    val duration = remember {
        Random.nextInt(300) + 300
    }

    val yNoiseAnimation = rememberInfiniteTransition()
    val yNoise by yNoiseAnimation.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = waterLevel, block = {
        val nextY = (waterLevel * height).toInt()
        val midPoint = height / 2
        val textHeight = with(density) { 100.sp.roundToPx() }
        y1 = if (nextY in (midPoint - textHeight)..(midPoint)) {
            lerp(midPoint - textHeight.toFloat(), (waterLevel * height), (1f - intensity) * .4f).toInt()
        } else {
            (waterLevel * height).toInt()
        }
        y1 = (y1 + yNoise).toInt()
    })

    val ay1 by animateIntAsState(
        targetValue = y1,
        animationSpec = spring(
            dampingRatio = 1f - intensity,
            stiffness = 100f, //Spring.StiffnessVeryLow
        )
    )

    return ay1
}

internal fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)
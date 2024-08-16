package waveprogress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import core.theme.MintGreen
import core.theme.OffWhite
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun WaveProgress(
    progress: Float, // This parameter represents the fill level of the wave (0.0 to 1.0)
    contentSize: IntSize, // This parameter allows you to specify the size of the wave container
    content: @Composable () -> Unit, // This parameter allows you to place content inside the wave
) = BoxWithConstraints {

    // Get the screen density
    val density = LocalDensity.current

    // Calculate the height of the wave based on the maximum height of the box
    val height = remember {
        with(density) { maxHeight.roundToPx() }
    }

    // Calculate three sets of Y coordinates for different wave intensities
    val aYs = calculateYs(height = height, progress = progress, intensityMultiplier = 0.7f, contentSize = contentSize)
    val aYs2 = calculateYs(height = height, progress = progress, intensityMultiplier = 0.5f, contentSize = contentSize)
    val aYs3 = calculateYs(height = height, progress = progress, intensityMultiplier = 0.3f, contentSize = contentSize)

    // Calculate the current Y position of the wave based on progress
    val currentY by remember(height, progress) {
        mutableStateOf(height * progress)
    }

    // Animate the current Y position with a spring animation
    val animatedY by animateFloatAsState(
        targetValue = currentY,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    // Draw the wave container
    Box(
        modifier = Modifier
            .background(MintGreen)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Draw the canvas for the wave
        Canvas(
            modifier = Modifier
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .fillMaxSize(),
            onDraw = {
                // Draw the first wave path with a vertical gradient
                drawPath(
                    path = ayPath(
                        aYs, size = this.size, currentY = currentY, animatedY = animatedY
                    ),
                    color = OffWhite,
                )

                // Draw the second wave path with transparency and light green color
                drawPath(
                    path = ayPath(
                        aYs2, size = this.size, currentY = currentY, animatedY = animatedY
                    ),
                    alpha = .7f,
                    color = OffWhite,
                )

                // Draw the third wave path with lower transparency and light green color
                drawPath(
                    path = ayPath(
                        aYs3, size = this.size, currentY = currentY, animatedY = animatedY
                    ),
                    alpha = .5f,
                    color = OffWhite,
                )
            }
        )

        // Place your content inside the wave (optional)
        content()
    }
}

fun ayPath(aYs: List<Int>, size: Size, currentY: Float, animatedY: Float): Path {
    // Create a new Path object to draw the wave shape
    return Path().apply {
        // Move the path to the top-left corner of the canvas
        moveTo(0f, 0f)

        // Draw a vertical line from the top-left corner to the current animated Y position
        lineTo(0f, animatedY)

        // Calculate the horizontal distance between each control point
        val interval = size.width * (1 / (aYs.size + 1).toFloat())

        // Iterate over the list of Y coordinates
        aYs.forEachIndexed { index, y ->
            // Calculate the X position for the current control point
            val segmentIndex = (index + 1) / (aYs.size + 1).toFloat()
            val x = size.width * segmentIndex

            // Add a cubic Bézier curve to the path
            cubicTo(
                // Control point 1: Adjust the X position based on the index to create a smoother curve
                x1 = if (index == 0) 0f else x - interval / 2f,
                // Control point 1: Use the previous Y coordinate or the current Y position if it's the first point
                y1 = aYs.getOrNull(index - 1)?.toFloat() ?: currentY,
                // Control point 2: Adjust the X position based on the index to create a smoother curve
                x2 = x - interval / 2f,
                // Control point 2: Use the current Y coordinate
                y2 = y.toFloat(),
                // End point of the curve: The current X and Y coordinates
                x3 = x,
                y3 = y.toFloat(),
            )
        }

        // Add the final cubic Bézier curve to connect the last point to the bottom-right corner
        cubicTo(
            // Control point 1: Adjust the X position for the last curve
            x1 = size.width - interval / 2f,
            // Control point 1: Use the last Y coordinate from the list
            y1 = aYs.last().toFloat(),
            // Control point 2: Set to the bottom-right corner of the canvas
            x2 = size.width,
            y2 = animatedY,
            // End point: Set to the bottom-right corner of the canvas
            x3 = size.width,
            y3 = animatedY,
        )

        // Draw a line from the bottom-right corner to the top-right corner
        lineTo(size.width, 0f)

        // Close the path to form a shape
        close()
    }
}

@Composable
fun calculateYs(
    height: Int,
    progress: Float,
    intensityMultiplier: Float,
    contentSize: IntSize
): List<Int> {
    val total = 6  // Number of points to calculate for the wave shape

    // Calculate Y coordinates for each point with varying intensity
    return (0..total).map {
        val half = total / 2
        val isBeyondHalfway = it > half

        calculateY(
            height = height, progress = progress,
            intensity = (if (isBeyondHalfway) total - it else it) / half * intensityMultiplier,
            contentSize = contentSize
        )
    }.toList()

}

@Composable
fun calculateY(
    height: Int,
    progress: Float,
    intensity: Float,
    contentSize: IntSize
): Int {
    var y1 by remember { mutableStateOf(0) }  // Initial Y position

    // Generate a random duration for the noise animation
    val duration = remember { Random.nextInt(300) + 300 }

    // Create an infinite animation for the noise effect
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

    // Calculate the target Y position based on water level and intensity
    LaunchedEffect(progress) {
        // Loop to make sure it keeps animating
        while(true) {
            // Calculate the next Y position based on the water level and the container height
            val nextY = (progress * height).toInt()

            // Calculate the middle point of the container
            val midPoint = height / 2

            // Calculate the height of the text in pixels
            val contentHeight = contentSize.height


            // Adjust the Y position based on the relative position of the next Y to the middle point and text height
            y1 = if (nextY in (midPoint - contentHeight)..(midPoint)) {
                // If the next Y position is close to the middle point, smoothly interpolate the Y position
                lerp(
                    start = midPoint - contentHeight.toFloat(),
                    stop = (progress * height),
//                   fraction = (1f - intensity) * 0.5f
                    fraction = 1f // Disable the intensity effect -> This is the effect that makes it so that it hangs for a bit on the content before springing past it
                ).toInt()
            } else {
                // Otherwise, set the Y position directly based on the water level
                (progress * height).toInt()
            }

            // Add noise to the Y position
            y1 = (y1 + yNoise).toInt()


            val delay = duration * 1.25f
            delay(delay.toLong())
        }
    }

    // Animate the Y position with a spring animation
    val ay1 by animateIntAsState(
        targetValue = y1,
        animationSpec = spring(
            dampingRatio = 1f - intensity,
            stiffness = 100f,
        )
    )

    return ay1
}

internal fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)
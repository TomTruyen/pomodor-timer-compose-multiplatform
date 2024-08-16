package animatedcounter

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset

@Composable
fun AnimatedCounter(
    count: Int,
    style: TextStyle,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<IntOffset> = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
) {
    var oldCount by remember { mutableStateOf(count) }
    
    // Update oldCount after composition so that we can use the old count for the animation
    SideEffect {
        oldCount = count
    }
    
    Row(modifier = modifier) {
        val countString = count.toString().padStart(2, '0')
        val oldCountString = oldCount.toString().padStart(2, '0')
        
        for(i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            
            AnimatedContent(
                targetState = if(oldChar == newChar) oldChar else newChar,
                transitionSpec = {
                    slideInVertically(
                        animationSpec = animationSpec,
                        initialOffsetY = { it }
                    ) togetherWith slideOutVertically(
                        animationSpec = animationSpec,
                        targetOffsetY = { -it }
                    )
                },
                label = "AnimatedCounter"
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}
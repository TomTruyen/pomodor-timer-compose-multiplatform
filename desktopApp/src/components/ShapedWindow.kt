package components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope

@Composable
fun FrameWindowScope.ShapedWindow(
    shape: Shape,
    content: @Composable () -> Unit
) {
    require(window.isTransparent) {
        "Window must have 'transparent' set to true for ShapedWindow to work correctly"
    }
    
    require(window.isUndecorated) {
        "Window must have 'undecorated' set to true for ShapedWindow to work correctly"
    }
    
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .shadow(3.dp, shape),
        shape = shape
    ) {
        content()
    }
}
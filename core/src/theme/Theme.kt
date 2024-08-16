package core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorPalette = lightColorScheme(
    primary = LightGreen,
    onPrimary = Color.White,
    secondary = MintGreen,
    onSecondary = Charcoal,
    surface = OffWhite,
    onSurface = Charcoal
)

//val DarkColorPalette = darkColorScheme(
//    primary = DarkGreen,
//    onPrimary = core.theme.getOffWhite,
//    surface = core.theme.getCharcoal,
//    onSurface = core.theme.getOffWhite
//)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if(darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }
    
    val colors = LightColorPalette
    
    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}

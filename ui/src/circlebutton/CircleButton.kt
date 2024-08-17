package circlebutton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    icon: ImageVector,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.hoverable(
            enabled = false,
            interactionSource = remember { MutableInteractionSource() }
        ).pointerHoverIcon(
            icon = PointerIcon.Hand
        ),
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color,
        ),
        elevation = null,
        border = BorderStroke(
            width = 1.dp,
            color = color,
            ),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            )
    }
}

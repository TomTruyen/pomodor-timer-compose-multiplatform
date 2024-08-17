package utils

import androidx.compose.ui.window.FrameWindowScope
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

fun FrameWindowScope.onFocusLost(block: () -> Unit) {
    window.addWindowFocusListener(object: WindowFocusListener {
        override fun windowGainedFocus(e: WindowEvent?) = Unit

        override fun windowLostFocus(e: WindowEvent?) = block()
    })
}
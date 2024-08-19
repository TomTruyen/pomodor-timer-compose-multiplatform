package utils

import androidx.compose.ui.Alignment

class TrayPositionProvider(
    platformProvider: PlatformProvider
) {
    private val platform by lazy {
        platformProvider.getCurrentPlatform()
    }
    
    // TODO: Replace the MAC implementation with actual JNI implementation for Tray Position: https://github.com/JetBrains/compose-multiplatform/issues/289#issuecomment-2267487501  
    fun calculateTrayPosition() = when(platform) {
        PlatformProvider.Platform.LINUX -> Alignment.TopEnd
        PlatformProvider.Platform.MAC -> Alignment.TopEnd
        PlatformProvider.Platform.WINDOWS, null -> Alignment.BottomEnd
    }
}


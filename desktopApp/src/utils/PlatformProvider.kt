package utils

class PlatformProvider {
    private val os by lazy {
        System.getProperty("os.name")
    }

    fun getCurrentPlatform() = when {
        os.containsIgnoreCase("linux") ->  Platform.LINUX
        os.containsIgnoreCase("mac") -> Platform.MAC
        os.containsIgnoreCase("windows") -> Platform.WINDOWS
        else -> null
    }

    private fun String.containsIgnoreCase(value: String) = contains(value, ignoreCase = true) 

    enum class Platform {
        LINUX,
        WINDOWS,
        MAC;
    }
}
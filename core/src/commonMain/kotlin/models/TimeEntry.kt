package core.models

/**
 * @param timeInSeconds in seconds
 * @param type of time entry
 * @param autoStart whether to start automatically
 */
data class TimeEntry(
    val timeInSeconds: Int,
    val type: TimeType,
    val autoStart: Boolean,
)
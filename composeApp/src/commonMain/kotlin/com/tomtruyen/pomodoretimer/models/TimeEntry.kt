package com.tomtruyen.pomodoretimer.models

/**
 * @param time in seconds
 * @param type of time entry
 * @param autoStart whether to start automatically
 */
data class TimeEntry(
    val time: Int,
    val type: TimeType,
    val autoStart: Boolean,
) {
    enum class TimeType {
        BREAK,
        FOCUS
    }
}
package com.tomtruyen.pomodoretimer.models

/**
 * @param name Name of the template
 * @param focusTimeSeconds Focus time in seconds
 * @param shortBreakTimeSeconds Short break time in seconds
 * @param longBreakTimeSeconds Long break time in seconds
 * @param autoStartFocus Whether to start the timer automatically when the focus session starts
 * @param autoStartBreak Whether to start the timer automatically when the short break session starts
 * @param focusSessionsBeforeLongBreak Amount of focus sessions before the long break starts
 */
data class Template(
    val name: String,
    val focusTimeSeconds: Int,
    val shortBreakTimeSeconds: Int,
    val longBreakTimeSeconds: Int,
    val autoStartFocus: Boolean,
    val autoStartBreak: Boolean,
    val focusSessionsBeforeLongBreak: Int,
) {
    /**
     * Generate the sequence of time entries based on the template
     *
     * @return [LinkedList] of [TimeEntry] objects
     */
    fun generateSequence(): LinkedList<TimeEntry> = LinkedList<TimeEntry>().apply {
        val sessionCount = focusSessionsBeforeLongBreak.coerceAtLeast(1)
        
        repeat(sessionCount) { count ->
            val focusEntry = TimeEntry(
                time = focusTimeSeconds,
                type = TimeEntry.TimeType.FOCUS,
                autoStart = autoStartFocus
            )
            
            val breakEntry = TimeEntry(
                time = if(count < sessionCount - 1) shortBreakTimeSeconds else longBreakTimeSeconds,
                type = TimeEntry.TimeType.BREAK,
                autoStart = autoStartBreak
            )

            add(focusEntry)
            add(breakEntry)
        }
    }

    companion object {
        val Default = Template(
            name = "Default",
            focusTimeSeconds = 25 * 60,
            shortBreakTimeSeconds = 5 * 60,
            longBreakTimeSeconds = 15 * 60,
            autoStartFocus = true,
            autoStartBreak = true,
            focusSessionsBeforeLongBreak = 4,
        )
    }
}
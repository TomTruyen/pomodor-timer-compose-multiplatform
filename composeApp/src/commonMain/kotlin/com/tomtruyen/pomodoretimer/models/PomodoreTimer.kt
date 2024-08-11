package com.tomtruyen.pomodoretimer.models

import kotlinx.coroutines.*

/**
 * Implementation of [Timer] for the Pomodoro Timer.
 *
 * @param template to generate the sequence for the [Timer]
 * @param scope the [CoroutineScope] to use for the [Timer] 
 */
class PomodoreTimer(
    template: Template = Template.Default,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
): Timer(
    sequence = template.generateSequence(),
    scope = scope
)
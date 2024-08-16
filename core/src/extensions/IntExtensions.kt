package extensions

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char
import kotlinx.datetime.format.optional

fun Int.hours() = this / 3600

fun Int.minutes() = (this % 3600) / 60

fun Int.seconds() = this % 60
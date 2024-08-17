package core.extensions

fun Int.hours() = this / 3600

fun Int.minutes() = (this % 3600) / 60

fun Int.seconds() = this % 60
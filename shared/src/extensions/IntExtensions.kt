package extensions

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char
import kotlinx.datetime.format.optional

fun Int.hours() = this / 3600

fun Int.minutes() = (this % 3600) / 60

fun Int.seconds() = this % 60

fun Int.asFormattedDuration(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    
    val localTime = LocalTime(
        hour = hours,
        minute = minutes,
        second = seconds
    )
    
    // Formats as HH:mm:ss or mm:ss 
    val formatter = LocalTime.Format {
        if(hours > 0) {
            hour()
            char(':')
        }
        
        minute()
        char(':')
        second()
    }
    
    return formatter.format(localTime)
}

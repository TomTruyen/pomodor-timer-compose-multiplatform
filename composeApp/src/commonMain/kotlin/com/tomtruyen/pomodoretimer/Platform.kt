package com.tomtruyen.pomodoretimer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
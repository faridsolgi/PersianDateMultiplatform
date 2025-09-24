package com.faridsolgi.persiandatemultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
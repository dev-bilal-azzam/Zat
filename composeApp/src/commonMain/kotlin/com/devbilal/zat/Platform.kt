package com.devbilal.zat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
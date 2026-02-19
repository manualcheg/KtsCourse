package com.manualcheg.ktscourse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
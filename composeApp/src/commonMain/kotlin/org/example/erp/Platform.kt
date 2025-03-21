package org.example.erp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
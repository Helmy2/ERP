package org.example.erp.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

fun Instant.toLocalString(): String {
    return this.format(
        format = DateTimeComponents.Format {
            dayOfMonth(); char('-'); monthNumber(); char('-'); year()
            char(' ');amPmHour(); char(':'); minute()
        }
    )
}
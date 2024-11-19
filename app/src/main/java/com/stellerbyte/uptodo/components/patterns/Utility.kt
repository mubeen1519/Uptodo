package com.stellerbyte.uptodo.components.patterns

import java.util.concurrent.TimeUnit

object Utility{
    const val TIME_COUNTDOWN: Long = 0L
    private const val TIME_FORMAT = "%02d:%02d"

    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

    fun getMillis(hours: Int, minutes: Int): Long {
        return (hours * 3600_000L) + (minutes * 60_000L)
    }

}
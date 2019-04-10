package com.kenzo.mobileplayer.util

object StringUtil {
    val HOUR = 60 * 60 * 1000
    val MIN = 60 * 1000
    val SEC = 1000
    fun parseDurion(process: Int): String {
        val hour = process / HOUR
        val min = process % HOUR / MIN
        val sec = process % MIN / SEC
        var result: String? = null
        if (hour == 0) {
            result = String.format("%02d:%02d", min, sec)
        } else {
            result = String.format("%02d:%02d:%02d", hour, min, sec)
        }
        return result
    }
}
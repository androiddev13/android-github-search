package com.githubsearch

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun String.fromISO8601ToUtc(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.parse(this)
}
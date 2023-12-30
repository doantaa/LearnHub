package com.cious.learnhub.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDate(
    format: String = "yyyy-MM-dd'T'HH:mm",
    destinationFormat: String = "dd MMMM yyy, HH:mm"
): String {
    return try {
        val sdfFormat = SimpleDateFormat(format, Locale.getDefault())
        val sdfDestination = SimpleDateFormat(destinationFormat, Locale.getDefault())
        val date = sdfFormat.parse(this)
        sdfDestination.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
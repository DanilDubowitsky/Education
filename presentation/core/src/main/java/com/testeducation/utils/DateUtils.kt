package com.testeducation.utils

import com.testeducation.domain.utils.HOUR_IN_SECONDS
import com.testeducation.domain.utils.MINUTE_IN_SECONDS
import com.testeducation.domain.utils.SECOND_IN_MILLIS
import java.text.SimpleDateFormat
import java.util.Locale

const val DAY_MONTH_YEAR_FULL = "d MMMM yyyy"

fun formatDate(
    format: String,
    dateInMillis: Long,
    locale: Locale = Locale.getDefault()
): String = SimpleDateFormat(format, locale).format(dateInMillis)

fun formatDateInSeconds(
    format: String,
    dateInSeconds: Long,
    locale: Locale = Locale.getDefault()
) = formatDate(format, dateInSeconds * SECOND_IN_MILLIS, locale)

fun getElapsedTime(timeInSeconds: Int): String {
    val formattedString = StringBuilder()

    val hours = timeInSeconds / HOUR_IN_SECONDS
    val minutesInSeconds = timeInSeconds % HOUR_IN_SECONDS
    val minutes = minutesInSeconds / MINUTE_IN_SECONDS
    val seconds = timeInSeconds % MINUTE_IN_SECONDS

    if (hours > 0) {
        formattedString.append("$hours ч ")
    }
    if (minutes > 0) {
        formattedString.append("$minutes м ")
    }
    if (seconds > 0) {
        formattedString.append("$seconds сек")
    }

    return formattedString.toString()
}

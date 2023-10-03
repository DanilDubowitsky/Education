package com.testeducation.utils

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

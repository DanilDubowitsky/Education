package com.testeducation.utils

import java.text.SimpleDateFormat
import java.util.Locale

const val DAY_MONTH_YEAR_FULL = "MMMM dd yyyy"

fun formatDate(
    format: String,
    dateInMillis: Long,
    locale: Locale = Locale.getDefault()
): String = SimpleDateFormat(format, locale).format(dateInMillis)

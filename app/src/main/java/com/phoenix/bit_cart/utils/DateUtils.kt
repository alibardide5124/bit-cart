package com.phoenix.bit_cart.utils

import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun String.toHumanReadableDate(): String {
    val dateTime = OffsetDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
    val formattedDate = Date.from(dateTime.toInstant())
    return SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a z", Locale.forLanguageTag("fa")).format(formattedDate)
}

fun String.toSimpleHumanReadableDate(): String {
    val dateTime = OffsetDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
    val formattedDate = Date.from(dateTime.toInstant())
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(formattedDate)
}
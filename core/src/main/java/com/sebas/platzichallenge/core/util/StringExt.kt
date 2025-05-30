package com.sebas.platzichallenge.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.formatDate(format: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    val date = LocalDate.parse(this, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd',' MMMM',' yyyy")
    return date.format(outputFormatter)
}


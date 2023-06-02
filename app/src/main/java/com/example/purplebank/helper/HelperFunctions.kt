package com.example.purplebank.helper

import com.example.purplebank.data.transaction.Amount
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


fun evaluateBalance(amount: Amount, amountToSend: String): Boolean {
    return if (amount.subUnits > 0) {
        val newAmount = BigDecimal.valueOf(amount.units.toLong())
            .multiply(BigDecimal.valueOf(100))
            .add(BigDecimal.valueOf(amount.subUnits.toLong()))
        newAmount >= BigDecimal(amountToSend).multiply(BigDecimal.valueOf(100))
    } else {
        BigDecimal.valueOf(amount.units.toLong()) >= BigDecimal(amountToSend)
    }
}


fun String.checkEntry(): Boolean {
    return this.isNotEmpty() && this.first() != '.' && this.matches(Regex("\\d*\\.?\\d{0,2}")) && !this.contains(
        ","
    )
}

fun fixDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateString, formatter)

    val dayOfMonth = dateTime.dayOfMonth
    val month = dateTime.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val year = dateTime.year
    val hour = dateTime.hour
    val minute = dateTime.minute
    return "$dayOfMonth${getDayOfMonthSuffix(dayOfMonth)} of $month $year, %02d:%02d".format(
        hour,
        minute
    )
}

fun getDayOfMonthSuffix(dayOfMonth: Int): String {
    return when (dayOfMonth) {
        1, 21, 31 -> "st"
        2, 22 -> "nd"
        3, 23 -> "rd"
        else -> "th"
    }
}
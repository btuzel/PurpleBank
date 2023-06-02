package com.example.purplebank.helper

import com.example.purplebank.data.transaction.Amount
import java.math.BigDecimal

open class HelperFunctions {

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
}

fun String.checkEntry(): Boolean {
    return this.isNotEmpty() && this.first() != '.' && this.matches(Regex("\\d*\\.?\\d{0,2}")) && !this.contains(",")
}
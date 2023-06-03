package com.example.purplebank.data.transaction

import java.io.Serializable

data class TransactionAmount(
    val amount: Amount,
    val currency: String
) : Serializable
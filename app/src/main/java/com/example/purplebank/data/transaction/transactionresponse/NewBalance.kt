package com.example.purplebank.data.transaction.transactionresponse

import com.example.purplebank.data.transaction.Amount
import java.io.Serializable

data class NewBalance(
    val currency: String,
    val amount: Amount
) : Serializable
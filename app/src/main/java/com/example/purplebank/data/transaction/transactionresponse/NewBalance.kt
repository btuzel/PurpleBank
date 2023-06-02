package com.example.purplebank.data.transaction.transactionresponse

import com.example.purplebank.data.transaction.Amount

data class NewBalance(
    val amount: Amount,
    val currency: String
)
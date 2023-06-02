package com.example.purplebank.data.user

import com.example.purplebank.data.transaction.Amount

data class MyBalance(
    val amount: Amount,
    val currency: String
)
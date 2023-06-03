package com.example.purplebank.data.user

import com.example.purplebank.data.transaction.Amount
import java.io.Serializable

data class MyBalance(
    val amount: Amount,
    val currency: String
) : Serializable
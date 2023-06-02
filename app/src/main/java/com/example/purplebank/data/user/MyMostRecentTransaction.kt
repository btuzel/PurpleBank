package com.example.purplebank.data.user

import com.example.purplebank.data.transaction.TransactionAmount

data class MyMostRecentTransaction(
    val amount: TransactionAmount,
    val date: String,
    val direction: String,
    val reference: String,
    val sender: String?
)
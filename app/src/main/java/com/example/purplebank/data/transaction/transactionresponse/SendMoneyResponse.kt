package com.example.purplebank.data.transaction.transactionresponse

import java.io.Serializable

data class SendMoneyResponse(
    val result: String,
    val newBalance: NewBalance?,
    val failureReason: String?
) : Serializable
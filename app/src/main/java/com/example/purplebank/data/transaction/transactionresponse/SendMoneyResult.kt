package com.example.purplebank.data.transaction.transactionresponse

sealed class SendMoneyResult {
    data class Success(val newBalance: NewBalance) : SendMoneyResult()
    data class Failure(val failureReason: String) : SendMoneyResult()
}
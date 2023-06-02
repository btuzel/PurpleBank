package com.example.purplebank.network.transaction

import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.transaction.transactionresponse.SendMoneyResult
import javax.inject.Inject

class SendMoneyUseCase @Inject constructor(private val sendMoneyService: SendMoneyService) {
    suspend operator fun invoke(
        transactionAmount: TransactionAmount,
        targetUser: String
    ): Result<SendMoneyResult> {
        return try {
            val response = sendMoneyService.sendMoney(transactionAmount, targetUser)
            Result.success(SendMoneyResult(response.newBalance))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
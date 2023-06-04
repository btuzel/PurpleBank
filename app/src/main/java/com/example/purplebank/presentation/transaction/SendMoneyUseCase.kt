package com.example.purplebank.presentation.transaction

import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.transaction.transactionresponse.SendMoneyResponse
import com.google.gson.Gson
import javax.inject.Inject

class SendMoneyUseCase @Inject constructor(private val sendMoneyService: SendMoneyService) {
    suspend operator fun invoke(
        transactionAmount: TransactionAmount,
        targetUser: String
    ): SendMoneyResponse {
        val response = sendMoneyService.sendMoney(transactionAmount, targetUser)
        val responseBody = response.string()
        return deserializeSendMoneyResponse(responseBody)
    }

    private fun deserializeSendMoneyResponse(jsonString: String): SendMoneyResponse {
        val gson = Gson()
        val response = gson.fromJson(jsonString, SendMoneyResponse::class.java)
        return SendMoneyResponse(
            result = response.result,
            newBalance = response.newBalance,
            failureReason = response.failureReason
        )
    }
}
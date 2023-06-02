package com.example.purplebank.network.transaction

import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.transaction.transactionresponse.NewBalance
import com.example.purplebank.data.transaction.transactionresponse.SendMoneyResult
import org.json.JSONObject
import javax.inject.Inject

class SendMoneyUseCase @Inject constructor(private val sendMoneyService: SendMoneyService) {
    suspend operator fun invoke(
        transactionAmount: TransactionAmount,
        targetUser: String
    ): SendMoneyResult {
        return try {
            val response = sendMoneyService.sendMoney(transactionAmount, targetUser)
            val responseBody = response.string()
            parseSendMoneyResult(responseBody)
        } catch (e: Exception) {
            SendMoneyResult.Failure(e.message ?: "Unknown error")
        }
    }

    private fun parseSendMoneyResult(responseBody: String): SendMoneyResult {
        val jsonObject = JSONObject(responseBody)
        val result = jsonObject.getString("result")
        return if (result == "ok") {
            val newBalanceJson = jsonObject.getJSONObject("newBalance")
            val currency = newBalanceJson.getString("currency")
            val amount = newBalanceJson.getJSONObject("amount")
            val units = amount.getInt("units")
            val subUnits = amount.getInt("subUnits")
            val newBalance = NewBalance(Amount(units, subUnits), currency)
            SendMoneyResult.Success(newBalance)
        } else {
            val failureReason = jsonObject.getString("failureReason")
            SendMoneyResult.Failure(failureReason)
        }
    }
}
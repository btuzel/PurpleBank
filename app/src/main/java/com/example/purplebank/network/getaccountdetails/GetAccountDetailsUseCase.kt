package com.example.purplebank.network.getaccountdetails

import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.user.MyBalance
import com.example.purplebank.data.user.MyMostRecentTransaction
import com.example.purplebank.data.user.User
import com.example.purplebank.data.user.UserResult
import org.json.JSONObject
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(private val getAccountDetailsService: GetAccountDetailsService) {
    suspend operator fun invoke(): UserResult {
        return try {
            val response = getAccountDetailsService.getUserAccount(API_TOKEN)
            val body = response.string()
            parseUserResult(body)
        } catch (e: Exception) {
            UserResult.Failure(e.message ?: "idk")
        }
    }

    private fun parseUserResult(jsonString: String): UserResult {
        val jsonObject = JSONObject(jsonString)
        val id = jsonObject.getString("id") ?: null
        if (!id.isNullOrEmpty()) {
            val name = jsonObject.getString("name")
            val myBalanceJson = jsonObject.getJSONObject("myBalance")
            val currency = myBalanceJson.getString("currency")
            val amountJson = myBalanceJson.getJSONObject("amount")
            val units = amountJson.getInt("units")
            val subUnits = amountJson.getInt("subUnits")
            val myBalance = MyBalance(Amount(units, subUnits), currency)
            val myMostRecentTransactionsJsonArray =
                jsonObject.getJSONArray("myMostRecentTransactions")
            val myMostRecentTransactions = mutableListOf<MyMostRecentTransaction>()
            for (i in 0 until myMostRecentTransactionsJsonArray.length()) {
                val transactionJson = myMostRecentTransactionsJsonArray.getJSONObject(i)
                val sender =
                    if (transactionJson.isNull("sender")) null else transactionJson.getString("sender")
                val reference = transactionJson.getString("reference")
                val date = transactionJson.getString("date")
                val direction = transactionJson.getString("direction")
                val transactionAmountJson = transactionJson.getJSONObject("amount")
                val transactionCurrency = transactionAmountJson.getString("currency")
                val transactionAmount = transactionAmountJson.getJSONObject("amount")
                val transactionUnits = transactionAmount.getInt("units")
                val transactionSubUnits = transactionAmount.getInt("subUnits")
                val transactionAmountData = TransactionAmount(
                    Amount(transactionUnits, transactionSubUnits),
                    transactionCurrency
                )
                val transaction = MyMostRecentTransaction(
                    transactionAmountData,
                    date,
                    direction,
                    reference,
                    sender
                )
                myMostRecentTransactions.add(transaction)
            }
            return UserResult.Success(User(id, myBalance, myMostRecentTransactions, name))
        } else {
            return UserResult.Failure("fail")
        }
    }
}

const val API_TOKEN = "1231231232"
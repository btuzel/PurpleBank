package com.example.purplebank.network.transaction

import com.example.purplebank.data.transaction.TransactionAmount
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Query

interface SendMoneyService {
    @POST("send-money")
    suspend fun sendMoney(
        //@Header("X-APP-ACCESS-TOKEN") apiKey: String,
        @Query("transactionAmount") transactionAmount: TransactionAmount,
        @Query("target-user") targetUser: String,
    ): ResponseBody
}
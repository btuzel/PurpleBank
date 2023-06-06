package com.example.purplebank.presentation.getaccountdetails

import com.example.purplebank.data.user.User
import com.example.purplebank.data.user.UserResult
import com.google.gson.Gson
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(private val getAccountDetailsService: GetAccountDetailsService) {
    suspend operator fun invoke(): UserResult {
        return try {
            val response = getAccountDetailsService.getUserAccount(API_TOKEN)
            val body = response.string()
            UserResult.Success(deserializeUserResponse(body))
        } catch (e: Exception) {
            UserResult.Failure(e.message ?: "Exception caught")
        }
    }

    private fun deserializeUserResponse(jsonString: String): User {
        val response = Gson().fromJson(jsonString, User::class.java)
        return User(
            response.id,
            response.myBalance,
            response.myMostRecentTransactions,
            response.name
        )
    }
}

const val API_TOKEN = "1231231232"
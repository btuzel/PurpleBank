package com.example.purplebank.network.getaccountdetails

import com.example.purplebank.data.user.User
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(private val getAccountDetailsService: GetAccountDetailsService) {
    suspend operator fun invoke(): User {
        return getAccountDetailsService.getUserAccount(API_TOKEN)
    }
}

const val API_TOKEN = "1231231232"
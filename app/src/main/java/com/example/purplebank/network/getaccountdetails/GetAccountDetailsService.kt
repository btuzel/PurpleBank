package com.example.purplebank.network.getaccountdetails

import com.example.purplebank.data.user.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GetAccountDetailsService {

    @GET("user-account")
    suspend fun getUserAccount(
        @Header("X-APP-ACCESS-TOKEN") apiKey: String,
    ): User
}

//purplebankbackend.com/user-account?userId=123, if we have userId
//apiToken goes with the headers
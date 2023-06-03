package com.example.purplebank.network.getaccountdetails

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header

interface GetAccountDetailsService {

    @GET("user-account")
    suspend fun getUserAccount(
        @Header("X-APP-ACCESS-TOKEN") apiKey: String,
    ): ResponseBody
}

//purplebankbackend.com/user-account?userId=123, if we have userId
//apiToken goes with the headers
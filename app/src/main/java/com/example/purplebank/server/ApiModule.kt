package com.example.purplebank.server

import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.presentation.getaccountdetails.GetAccountDetailsService
import com.example.purplebank.presentation.transaction.SendMoneyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.io.IOException

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideGetAccountDetailsService(
        retrofit: Retrofit,
        mockEnvironmentConfig: MockEnvironmentConfig,
    ): GetAccountDetailsService {
        val getAccService = retrofit.create(GetAccountDetailsService::class.java)
        return NetworkErrorSimulatingAccountService(
            getAccService,
            mockEnvironmentConfig
        )
    }

    @Provides
    fun provideSendMoneyService(
        retrofit: Retrofit,
        mockEnvironmentConfig: MockEnvironmentConfig,
    ): SendMoneyService {
        val sendMoneyService = retrofit.create(SendMoneyService::class.java)
        return NetworkErrorSimulatingSendMoneyService(
            sendMoneyService,
            mockEnvironmentConfig
        )
    }
}

private class NetworkErrorSimulatingAccountService(
    private val accService: GetAccountDetailsService,
    private val mockEnvironmentConfig: MockEnvironmentConfig
) : GetAccountDetailsService {
    override suspend fun getUserAccount(apiKey: String): ResponseBody {
        if (mockEnvironmentConfig.userAccountsResponse == Response.NETWORK_ERROR) {
            throw IOException("Something went wrong")
        }
        return accService.getUserAccount(apiKey)
    }
}

private class NetworkErrorSimulatingSendMoneyService(
    private val moneyService: SendMoneyService,
    private val mockEnvironmentConfig: MockEnvironmentConfig
) : SendMoneyService {
    override suspend fun sendMoney(
        transactionAmount: TransactionAmount,
        targetUser: String
    ): ResponseBody {
        if (mockEnvironmentConfig.sendMoneyResponse == Response.NETWORK_ERROR) {
            throw IOException("Something went wrong")
        }
        return moneyService.sendMoney(transactionAmount, targetUser)
    }
}

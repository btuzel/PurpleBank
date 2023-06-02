package com.example.purplebank.server

import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.transaction.transactionresponse.SendMoneyResult
import com.example.purplebank.data.user.User
import com.example.purplebank.network.getaccountdetails.GetAccountDetailsService
import com.example.purplebank.network.transaction.SendMoneyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import retrofit2.Retrofit

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
    override suspend fun getUserAccount(apiKey: String): User {
        if (mockEnvironmentConfig.metadataResponse == Response.NETWORK_ERROR) {
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
    ): SendMoneyResult {
        if (mockEnvironmentConfig.metadataResponse == Response.NETWORK_ERROR) {
            throw IOException("Something went wrong")
        }
        return moneyService.sendMoney(transactionAmount, targetUser)
    }
}

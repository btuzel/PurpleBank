package com.example.purplebank.di

import com.example.purplebank.network.getaccountdetails.GetAccountDetailsService
import com.example.purplebank.network.transaction.SendMoneyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

//    @Provides
//    fun provideGetAccountDetailsService(retrofit: Retrofit): GetAccountDetailsService {
//        return retrofit.create(GetAccountDetailsService::class.java)
//    }

//    @Provides
//    fun provideSendMoneyService(retrofit: Retrofit): SendMoneyService {
//        return retrofit.create(SendMoneyService::class.java)
//    }
}

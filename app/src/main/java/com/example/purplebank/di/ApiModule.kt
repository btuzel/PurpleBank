package com.example.purplebank.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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

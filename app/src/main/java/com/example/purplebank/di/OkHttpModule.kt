package com.example.purplebank.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
class OkHttpModule {
    @Provides
    fun provideOkHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()
    }

    @Provides
    @ElementsIntoSet
    fun provideInterceptors(): Set<Interceptor> {
        return emptySet()
    }
}

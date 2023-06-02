package com.example.purplebank.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRegistrationRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(buildGsonConverterFactory(gson))
            .client(okHttpClient)
            .build()

    private fun buildGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}
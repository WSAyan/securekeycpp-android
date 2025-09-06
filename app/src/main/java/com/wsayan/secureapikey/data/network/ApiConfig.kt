package com.wsayan.secureapikey.data.network

import com.wsayan.secureapikey.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = BuildConfig.BASE_URL

    suspend fun createOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authKeyInterceptor: AuthKeyInterceptor
    ): OkHttpClient = withContext(Dispatchers.IO) {
        OkHttpClient.Builder()
            .addInterceptor(authKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun apiService(httpClient: OkHttpClient, converter: GsonConverterFactory): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .client(httpClient)
            .build()
            .create(ApiService::class.java)

}
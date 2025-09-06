package com.wsayan.secureapikey.data.network

import com.wsayan.secureapikey.BuildConfig
import com.wsayan.secureapikey.NativeAuthKey
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
    ): OkHttpClient = withContext(Dispatchers.IO) {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                val authKey = NativeAuthKey.generateAuthKey()
                println("----------> $authKey")

                if (authKey.isNotEmpty()) {
                    requestBuilder.header("AuthKey", authKey)
                }

                chain.proceed(requestBuilder.build())
            }
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
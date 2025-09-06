package com.wsayan.secureapikey.data

import com.wsayan.secureapikey.data.network.ApiConfig
import com.wsayan.secureapikey.data.network.ApiService
import com.wsayan.secureapikey.data.repository.AnimeRepositoryImpl
import com.wsayan.secureapikey.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

fun dataModule() = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        runBlocking(Dispatchers.IO) {
            ApiConfig.createOkHttpClient(get())
        }
    }

    single {
        GsonConverterFactory.create()
    }

    single<ApiService> {
        ApiConfig.apiService(get(), get())
    }

    single<AnimeRepository> { AnimeRepositoryImpl(get()) }
}
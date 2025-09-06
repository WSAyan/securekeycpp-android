package com.wsayan.secureapikey.data.network

import com.wsayan.secureapikey.utils.NativeAuthKey
import okhttp3.Interceptor
import okhttp3.Response

class AuthKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        NativeAuthKey.generateAuthKey().let { authKey ->
            requestBuilder.header("AuthKey", authKey)
        }

        return chain.proceed(requestBuilder.build())
    }
}
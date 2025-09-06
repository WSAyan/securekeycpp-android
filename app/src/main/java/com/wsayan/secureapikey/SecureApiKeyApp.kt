package com.wsayan.secureapikey

import android.app.Application
import com.wsayan.secureapikey.di.initKoin

class SecureApiKeyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}
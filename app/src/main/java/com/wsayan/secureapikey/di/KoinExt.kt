package com.wsayan.secureapikey.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun Context.initKoin(): KoinApplication {
    stopKoin()

    return startKoin {
        androidContext(this@initKoin)
        androidLogger()
        modules(coreModule)
    }
}
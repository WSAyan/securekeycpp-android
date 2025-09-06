package com.wsayan.secureapikey.utils

object NativeAuthKey {
    init {
        System.loadLibrary("native-lib")
    }

    external fun generateAuthKey(): String
}
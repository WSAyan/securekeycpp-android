package com.wsayan.secureapikey

object NativeAuthKey {
    init {
        System.loadLibrary("native-lib")
    }

    external fun generateAuthKey(): String
}
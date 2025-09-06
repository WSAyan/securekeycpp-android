package com.wsayan.secureapikey

object ApiKeyExtractor {
    private external fun getApiKey(operationId: Int, source: String): String

    fun getKey(): String {
        return getApiKey(2, "36155243 3498369047 1021536022 2973941993 3455464123 769477641 3350512168 361146780 786222820 4168595372 2025772325 68777176 813095261 474727908 409731493 1471849523")
    }

    init {
        println("####################")
        System.loadLibrary("native-lib")
    }
}
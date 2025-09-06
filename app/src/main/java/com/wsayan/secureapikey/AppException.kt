package com.wsayan.secureapikey

sealed class AppException(override val message: String, val code: Int = -1) : Exception(message) {
    class Network(message: String? = null) : AppException(message ?: "Network connection error")
    class Database(message: String? = null) : AppException(message ?: "Database operation failed")
    class Server(message: String? = null, code: Int) :
        AppException(message ?: "Server error (code $code)", code)

    class DataIntegrity(message: String? = null) :
        AppException(message ?: "Data integrity violation")

    class Unknown(message: String? = null) : AppException(message ?: "An unknown error occurred")

    class Authentication(message: String? = null) : AppException(message ?: "Authentication error")
}
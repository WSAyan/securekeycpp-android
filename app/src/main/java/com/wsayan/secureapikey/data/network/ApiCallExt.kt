package com.wsayan.secureapikey.data.network

import com.google.gson.Gson
import com.wsayan.secureapikey.AppException
import com.wsayan.secureapikey.data.network.dto.BaseResponseDto
import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (exception: IOException) {
        throw AppException.Network(message = exception.message)
    } catch (exception: HttpException) {
        val errorBody = exception.parseErrorBody<BaseResponseDto>()

        throw AppException.Server(
            message = errorBody?.message ?: "Unknown server error",
            code = exception.code()
        )
    } catch (exception: Exception) {
        throw AppException.Unknown(message = exception.message)
    }
}

inline fun <reified T> HttpException.parseErrorBody(): T? {
    return try {
        val errorBody = response()?.errorBody()?.string()
        if (errorBody.isNullOrEmpty()) return null
        Gson().fromJson(errorBody, T::class.java)
    } catch (e: Exception) {
        null
    }
}
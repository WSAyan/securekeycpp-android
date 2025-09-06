package com.wsayan.secureapikey.domain

import com.wsayan.secureapikey.AppException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class ResourceState<out T> {
    data object Loading : ResourceState<Nothing>()
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error(val message: String) : ResourceState<Nothing>()
}

inline fun <reified T> resourceFlow(
    crossinline call: suspend () -> ResourceState<T>
): Flow<ResourceState<T>> = flow {
    emit(ResourceState.Loading)
    emit(call())
}
    .flowOn(Dispatchers.IO)
    .catch {
        it.printStackTrace()
        emit(ResourceState.Error(if (it is AppException) it.message else "An unexpected error occurred"))
    }
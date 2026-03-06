package com.devbilal.data.utils

import com.devbilal.domain.exception.UnknownException
import com.devbilal.domain.exception.ZatException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch


suspend fun <T> safeCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        throw e.toZatException()
    }
}

fun <T> safeFlow(block: () -> Flow<T>): Flow<T> {
    return block().catch { e ->
        throw e.toZatException()
    }
}

fun Throwable.toZatException(): ZatException = when (this) {
    is ZatException -> this
    else -> UnknownException()
}

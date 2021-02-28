package com.tom.learncoroutinexroom.extensions

import com.tom.learncoroutinexroom.common.Result

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        Result.error(e.message ?: "")
    }
}
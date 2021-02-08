package com.tom.learncoroutinexroom.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tom.learncoroutinexroom.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

fun <L, R> resultLiveData(
    networkCall: suspend () -> kotlin.Result<R>,
    saveCallResult: suspend (R) -> Unit,
    io: CoroutineDispatcher
): LiveData<Result<L>> =
    liveData(io) {
        emit(Result.loading<L>())
        delay(1_500)
    }

package com.tom.learncoroutinexroom.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tom.learncoroutinexroom.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

fun <R> resultLiveData(
    networkCall: suspend () -> Result<R>,
    io: CoroutineDispatcher
): LiveData<Result<R>> =
    liveData(io) {
        emit(Result.loading<R>())
        delay(1_500)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            responseStatus.data?.let { }
        } else if (responseStatus.status == Result.Status.ERROR) {
            if (responseStatus.message != null) {
                emit(Result.error<R>(responseStatus.message))
            }
        }
    }
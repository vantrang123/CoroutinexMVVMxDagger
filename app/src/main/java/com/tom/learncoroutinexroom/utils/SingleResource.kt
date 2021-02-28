package com.tom.learncoroutinexroom.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.data.model.Player
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

fun <R> resultLiveData(
        networkCall: suspend () -> Result<R>,
        saveCallResult: suspend (R) -> Unit,
        io: CoroutineDispatcher
): LiveData<Result<R>> =
        liveData(io) {
            emit(Result.loading<R>())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Result.Status.SUCCESS) {
                responseStatus.data?.let {
                    saveCallResult(it)
                    emit(Result.success(it))
                }
            } else if (responseStatus.status == Result.Status.ERROR) {
                if (responseStatus.message != null) {
                    emit(Result.error<R>(responseStatus.message))
                }
            }
        }
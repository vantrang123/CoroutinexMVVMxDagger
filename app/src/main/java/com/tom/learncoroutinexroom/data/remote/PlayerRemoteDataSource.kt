package com.tom.learncoroutinexroom.data.remote

import com.tom.learncoroutinexroom.base.BaseDataSource
import javax.inject.Inject

class PlayerRemoteDataSource @Inject constructor(private val service: Service) : BaseDataSource() {
    suspend fun getAllPlayers() = getResult {
        service.getAllPlayers()
    }
}
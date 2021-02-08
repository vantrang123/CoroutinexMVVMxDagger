package com.tom.learncoroutinexroom.data

import com.tom.learncoroutinexroom.data.remote.PlayerRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val remote: PlayerRemoteDataSource,
    @Named("IO") private val io: CoroutineDispatcher = IO
) {

}
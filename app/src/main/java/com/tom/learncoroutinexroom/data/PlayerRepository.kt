package com.tom.learncoroutinexroom.data

import androidx.lifecycle.LiveData
import com.tom.learncoroutinexroom.data.local.DbService
import com.tom.learncoroutinexroom.data.model.Player
import com.tom.learncoroutinexroom.data.remote.PlayerRemoteDataSource
import com.tom.learncoroutinexroom.utils.resultLiveData
import io.realm.Realm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val remote: PlayerRemoteDataSource,
    private val dbService: DbService,
    private val realm: Realm,
    @Named("IO") private val io: CoroutineDispatcher = IO
) {
    fun observePlayer() = resultLiveData(
        networkCall = { remote.getAllPlayers() },
        saveCallResult = { dbService.saveAll(it) },
        io = io
    )

    suspend fun observePlayerByUUID(id: String) = dbService.getPlayer(playerId = id)
}
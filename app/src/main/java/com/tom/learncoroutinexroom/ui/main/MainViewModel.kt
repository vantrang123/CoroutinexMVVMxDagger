package com.tom.learncoroutinexroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tom.learncoroutinexroom.base.BaseViewModel
import com.tom.learncoroutinexroom.data.local.DbService
import com.tom.learncoroutinexroom.data.repository.PlayerRepository
import com.tom.learncoroutinexroom.data.model.Player
import com.tom.learncoroutinexroom.data.model.error.ErrorMessage
import com.tom.learncoroutinexroom.extensions.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(
        private val repository: PlayerRepository,
        private val dbService: DbService,
        @Named("IO") private val io: CoroutineDispatcher,
        @Named("MAIN") private val main: CoroutineDispatcher
) : BaseViewModel() {

    private val _players = MutableLiveData<List<Player>>()
    val players: LiveData<List<Player>> get() = _players

    fun getListPlayers() {
        viewModelScope.launch(main) {
            try {
                isLoading.postValue(true)
                val result = async(context = io) {
                    repository.observerPlayers()
                }
                result.await().apply {
                    _players.postValue(body())
                    isLoading.postValue(false)
                    if (body() != null) dbService.saveAll(body()!!)
                }
            } catch (e: Throwable) {
                error.postValue(ErrorMessage(message = e.message ?: "Unknown error"))
            }
        }
    }
}
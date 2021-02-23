package com.tom.learncoroutinexroom.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.data.PlayerRepository
import com.tom.learncoroutinexroom.data.model.Player
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class DetailViewModel @Inject constructor(
        private val repository: PlayerRepository,
        @Named("IO") private val io: CoroutineDispatcher,
        @Named("MAIN") private val main: CoroutineDispatcher
) : ViewModel() {
    private val _player = MutableLiveData<Result<Player?>>()
    val player: LiveData<Result<Player?>> get() = _player

    private fun setResultPlayer(result: Result<Player?>) {
        result.data?.let {
            _player.postValue(result)
        }
    }

    internal fun observePlayerByUUID(id: String) {
        viewModelScope.launch(main) {
            try {
                setResultPlayer(Result.loading())
                delay(1_500)
                val result = async(context = io) {
                    repository.observePlayerByUUID(id = id)
                }
                setResultPlayer(Result.success(result.await()))
            } catch (e: Throwable) {
                setResultPlayer(Result.error(e.message ?: "Unknown error"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
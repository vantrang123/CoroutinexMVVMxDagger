package com.tom.learncoroutinexroom.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.learncoroutinexroom.base.BaseViewModel
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.data.PlayerRepository
import com.tom.learncoroutinexroom.data.model.Player
import com.tom.learncoroutinexroom.data.model.error.ErrorMessage
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class DetailViewModel @Inject constructor(
        private val repository: PlayerRepository,
        @Named("IO") private val io: CoroutineDispatcher,
        @Named("MAIN") private val main: CoroutineDispatcher
) : BaseViewModel() {
    private val _player = MutableLiveData<Player?>()
    val player: LiveData<Player?> get() = _player

    private fun setResultPlayer(result: Player?) {
        _player.postValue(result)
        isLoading.postValue(false)
    }

    internal fun observePlayerByUUID(id: String) {
        viewModelScope.launch(main) {
            try {
                isLoading.postValue(true)
                delay(1_500)
                val result = async(context = io) {
                    repository.observePlayerByUUID(id = id)
                }
                setResultPlayer(result.await())
            } catch (e: Throwable) {
                error.postValue(ErrorMessage(message = e.message ?: "Unknown error"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
package com.tom.learncoroutinexroom.ui.main

import androidx.lifecycle.ViewModel
import com.tom.learncoroutinexroom.base.BaseViewModel
import com.tom.learncoroutinexroom.data.PlayerRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: PlayerRepository
) : BaseViewModel() {
    val player by lazy {
        repository.observePlayer()
    }
}
package com.tom.learncoroutinexroom.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.learncoroutinexroom.data.model.error.ErrorMessage

abstract class BaseViewModel : ViewModel(), IViewModel {
    override var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    override var error: MutableLiveData<ErrorMessage> = MutableLiveData()
}
package com.tom.learncoroutinexroom.base

import androidx.lifecycle.MutableLiveData
import com.tom.learncoroutinexroom.data.model.error.ErrorMessage

interface IViewModel {
    var isLoading: MutableLiveData<Boolean>
    var error: MutableLiveData<ErrorMessage>
}
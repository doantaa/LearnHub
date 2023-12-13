package com.cious.learnhub.ui.authentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _otpRequestResult = MutableLiveData<ResultWrapper<Boolean>>()
    val otpRequestResult : LiveData<ResultWrapper<Boolean>>
        get() = _otpRequestResult

    fun sendOtpRequest(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.sendOtpRequest(email)
                .collect{
                    _otpRequestResult.postValue(it)
                }
        }
    }


}
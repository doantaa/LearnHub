package com.cious.learnhub.ui.authentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.OtpData
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _otpRequestResult = MutableLiveData<ResultWrapper<OtpData>>()
    val otpRequestResult : LiveData<ResultWrapper<OtpData>>
        get() = _otpRequestResult

    fun sendOtpRegister(otpRequest: OtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.sendOtpRegister(otpRequest)
                .collect{
                    _otpRequestResult.postValue(it)
                }
        }
    }
}
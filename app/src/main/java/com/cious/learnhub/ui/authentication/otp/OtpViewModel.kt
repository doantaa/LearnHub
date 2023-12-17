package com.cious.learnhub.ui.authentication.otp

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtpViewModel(
    private val authRepository: AuthRepository,
    private val extras: Bundle?
): ViewModel() {

    val dataParcel = extras?.getParcelable<AuthenticationData>(OtpActivity.USER_REGISTER_DATA)
    private val _registerResult = MutableLiveData<ResultWrapper<Boolean>>()

    val registerResult : LiveData<ResultWrapper<Boolean>>
        get() = _registerResult

    fun doRegister(authenticationData: AuthenticationData, otp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.doRegister(authenticationData, otp)
                .collect{
                    _registerResult.postValue(it)
                }
        }
    }
}
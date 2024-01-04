package com.cious.learnhub.ui.authentication.otp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.model.RegisterData
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtpViewModel(
    private val extras: Bundle?,
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
): ViewModel() {

    val dataParcel = extras?.getParcelable<AuthenticationData>(OtpActivity.USER_REGISTER_DATA)

    private val _registerResult = MutableLiveData<ResultWrapper<RegisterData>>()
    val registerResult : LiveData<ResultWrapper<RegisterData>>
        get() = _registerResult

    val isLogin = authRepository.isLogin()

    fun saveAuthToken(token: String) {
        sessionManager.saveAuthToken(token)
    }

    fun doRegister(authenticationData: AuthenticationData, otp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.doRegister(authenticationData, otp)
                .collect{
                    _registerResult.postValue(it)
                }
        }
    }
}
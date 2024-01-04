package com.cious.learnhub.ui.authentication.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.LoginData
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _loginRequestResult = MutableLiveData<ResultWrapper<LoginData>>()
    val loginRequestResult: LiveData<ResultWrapper<LoginData>>
        get() = _loginRequestResult

    fun doLoginRequest(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.doLogin(loginRequest).collect {
                    _loginRequestResult.postValue(it)
                }
        }
    }
}
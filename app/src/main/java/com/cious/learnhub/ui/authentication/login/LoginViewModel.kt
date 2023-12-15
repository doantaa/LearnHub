package com.cious.learnhub.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.UserPreferenceDataStore
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreferenceDataStore: UserPreferenceDataStore
): ViewModel() {

    private val _loginRequestResult = MutableLiveData<ResultWrapper<String>>()
    val loginRequestResult: LiveData<ResultWrapper<String>>
        get() = _loginRequestResult

    fun doLoginRequest(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.doLogin(loginRequest)
                .collect{
                    _loginRequestResult.postValue(it)
                }
        }
    }
}
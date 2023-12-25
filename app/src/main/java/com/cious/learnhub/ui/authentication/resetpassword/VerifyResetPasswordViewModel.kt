package com.cious.learnhub.ui.authentication.resetpassword

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.UserOtpPasswordData
import com.cious.learnhub.model.VerifyResetPasswordData
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerifyResetPasswordViewModel(
    private val extras: Bundle?,
    private val authRepository: AuthRepository
) : ViewModel() {

    val dataParcel =
        extras?.getParcelable<UserOtpPasswordData>(VerifyResetPasswordActivity.USER_OTP_PASSWORD_DATA)

    private val _resetPasswordResult = MutableLiveData<ResultWrapper<VerifyResetPasswordData>>()
    val resetPasswordResult: LiveData<ResultWrapper<VerifyResetPasswordData>>
        get() = _resetPasswordResult

    fun doResetPassword(verifyResetPasswordRequest: VerifyResetPasswordRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.doResetPassword(verifyResetPasswordRequest)
                .collect {
                    _resetPasswordResult.postValue(it)
                }
        }
    }
}
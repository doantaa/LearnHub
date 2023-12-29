package com.cious.learnhub.ui.profile.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.ChangePasswordModel
import com.cious.learnhub.model.UserEditModel
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePasswordViewModel (
    private val repository: ProfileRepository
): ViewModel() {
        private val _changePasswordRequestResult = MutableLiveData<ResultWrapper<ChangePasswordModel>>()
        val changePasswordRequestResult: LiveData<ResultWrapper<ChangePasswordModel>>
        get() = _changePasswordRequestResult

        fun doChangePassword(changePasswordRequest: ChangePasswordRequest) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.doChangePassword(changePasswordRequest)
                    .collect {
                        _changePasswordRequestResult.postValue(it)
                    }
            }
        }

    }

package com.cious.learnhub.ui.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.UserEditModel
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _profileRequestResult = MutableLiveData<ResultWrapper<UserEditModel>>()
    val profilefRequestResult: LiveData<ResultWrapper<UserEditModel>>
        get() = _profileRequestResult

    fun doEditData(profileRequest: ProfileRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.doEditData(profileRequest)
                .collect {
                    _profileRequestResult.postValue(it)
                }
        }
    }
    val profile = repository.getProfile().asLiveData(Dispatchers.IO)
}

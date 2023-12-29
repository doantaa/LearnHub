package com.cious.learnhub.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.model.UserEditModel
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {


}
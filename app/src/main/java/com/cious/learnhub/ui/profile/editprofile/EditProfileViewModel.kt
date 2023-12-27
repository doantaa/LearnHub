package com.cious.learnhub.ui.profile.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.data.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers

class EditProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    val profile = repository.getProfile().asLiveData(Dispatchers.IO)
}
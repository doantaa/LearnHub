package com.cious.learnhub.ui.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.model.UserEditModel
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val prefs: SharedPreferences,
    private val sessionManager: SessionManager
) : ViewModel() {

    val isLogin = authRepository.isLogin()

    fun clearToken() {
        sessionManager.clearData()
    }
}
package com.cious.learnhub.ui.profile

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.utils.SessionManager

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val prefs: SharedPreferences,
    private val sessionManager: SessionManager
) : ViewModel() {

    val isLogin = authRepository.isLogin()

    fun clearToken() = authRepository.clearToken()
}
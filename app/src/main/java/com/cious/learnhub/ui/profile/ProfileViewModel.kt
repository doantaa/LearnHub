package com.cious.learnhub.ui.profile

import androidx.lifecycle.ViewModel
import com.cious.learnhub.data.repository.AuthRepository

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLogin = authRepository.isLogin()

    fun clearToken() = authRepository.clearToken()
}
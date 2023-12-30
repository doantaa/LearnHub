package com.cious.learnhub.ui.main

import androidx.lifecycle.ViewModel
import com.cious.learnhub.data.repository.AuthRepository

class MainViewModel(private val authRepository: AuthRepository) : ViewModel(){
    val isLogin = authRepository.isLogin()
}
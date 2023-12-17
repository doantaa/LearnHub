package com.cious.learnhub.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cious.learnhub.data.repository.NotifiacationRepository
import kotlinx.coroutines.Dispatchers

class NotificationsViewModel(
    private val repository: NotifiacationRepository
) : ViewModel() {
    val notification = repository.getNotification().asLiveData(Dispatchers.IO)
}
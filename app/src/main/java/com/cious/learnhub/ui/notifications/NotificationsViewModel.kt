package com.cious.learnhub.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cious.learnhub.data.repository.NotifiacationRepository
import kotlinx.coroutines.Dispatchers

class NotificationsViewModel(
    private val repository: NotifiacationRepository
) : ViewModel() {
    val notification = repository.getNotification().asLiveData(Dispatchers.IO)

    fun markedNotification(id: Int) = repository.markNotification(id).asLiveData(Dispatchers.IO)
}
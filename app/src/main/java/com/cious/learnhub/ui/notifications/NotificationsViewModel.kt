package com.cious.learnhub.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val notifRepository: NotifiacationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _notifRequestResult = MutableLiveData<ResultWrapper<List<NotificationModel>>>()
    val notifRequestResult: LiveData<ResultWrapper<List<NotificationModel>>>
        get() = _notifRequestResult

    val isLogin = authRepository.isLogin()

    fun getNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            notifRepository.getNotification()
                .collect {
                    _notifRequestResult.postValue(it)
                }
        }
    }

}
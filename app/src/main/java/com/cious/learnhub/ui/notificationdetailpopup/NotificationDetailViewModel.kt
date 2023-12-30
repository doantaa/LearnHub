package com.cious.learnhub.ui.notificationdetailpopup

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.model.MarkAsReadNotification
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationDetailViewModel (
    private val extras: Bundle?,
    private val notifiacationRepository:NotifiacationRepository
):ViewModel(){
    val notification = extras?.getParcelable<NotificationModel>(NotificationDetailBottomSheet.EXTRA_NOTIFICATION)

}
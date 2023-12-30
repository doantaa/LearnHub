package com.cious.learnhub.ui.notifications.notificationdetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.model.NotificationModel

class NotificationDetailViewModel (
    private val extras: Bundle?,
    private val notifiacationRepository:NotifiacationRepository
):ViewModel(){
    val notification = extras?.getParcelable<NotificationModel>(NotificationDetailActivity.EXTRA_NOTIFICATION)

}
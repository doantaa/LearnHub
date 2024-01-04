package com.cious.learnhub.ui.notifications.notificationdetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.cious.learnhub.model.NotificationModel

class NotificationDetailViewModel (
    private val extras: Bundle?
):ViewModel(){
    val notification = extras?.getParcelable<NotificationModel>(NotificationDetailActivity.EXTRA_NOTIFICATION)

}
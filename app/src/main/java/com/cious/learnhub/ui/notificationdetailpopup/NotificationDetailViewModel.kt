package com.cious.learnhub.ui.notificationdetailpopup

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.cious.learnhub.model.NotificationModel

class NotificationDetailViewModel (
    private val extras: Bundle?
):ViewModel(){
    val notification = extras?.getParcelable<NotificationModel>(NotificationDetailBottomSheet.EXTRA_NOTIFICATION)

}
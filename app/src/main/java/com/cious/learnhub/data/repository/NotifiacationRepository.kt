package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.NotificaitonDataSource
import com.cious.learnhub.data.network.api.model.notification.toNotificationData
import com.cious.learnhub.data.network.api.model.notification.toNotificationList
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface NotifiacationRepository {
    fun getNotification(): Flow<ResultWrapper<List<NotificationModel>>>
}

class NotificationRepositoryImpl(
    private val dataSource: NotificaitonDataSource
) : NotifiacationRepository {
    override fun getNotification(): Flow<ResultWrapper<List<NotificationModel>>> {
        return proceedFlow {
            dataSource.getNotification().data?.toNotificationList() ?: emptyList()
        }
    }

}
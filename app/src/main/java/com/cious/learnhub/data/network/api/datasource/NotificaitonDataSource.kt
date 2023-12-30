package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.notification.MarkAsReadNotificationResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.data.network.api.service.NotificationService

interface NotificaitonDataSource {
    suspend fun getNotification(): NotificationResponse
    suspend fun markAsReadNotification(id:Int): MarkAsReadNotificationResponse
}

class NotificationDataSourceImpl(
    private val service: NotificationService
): NotificaitonDataSource {

    override suspend fun getNotification(): NotificationResponse {
        return service.getNotification()
    }

    override suspend fun markAsReadNotification(id: Int): MarkAsReadNotificationResponse {
        return service.markAsReadNotification(id)
    }


}
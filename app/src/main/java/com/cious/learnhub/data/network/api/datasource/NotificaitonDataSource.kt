package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.data.network.api.service.NotificationService

interface NotificaitonDataSource {
    suspend fun getNotification(): NotificationResponse

    suspend fun markNotification(id: Int): NotificationResponse
}

class NotificationDataSourceImpl(
    private val service: NotificationService
): NotificaitonDataSource {
    override suspend fun getNotification(): NotificationResponse {
        return service.getNotification()
    }

    override suspend fun markNotification(id: Int): NotificationResponse {
        return service.markReadNotification(id)
    }
}
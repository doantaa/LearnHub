package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.NotificaitonDataSource
import com.cious.learnhub.data.network.api.model.notification.toNotificationList
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface NotifiacationRepository {
    fun getNotification(

    ): Flow<ResultWrapper<List<NotificationModel>>>

    fun markNotification(id: Int): Flow<ResultWrapper<Boolean>>
}

class NotificationRepositoryImpl(
    private val dataSource: NotificaitonDataSource
) : NotifiacationRepository {
    override fun getNotification(): Flow<ResultWrapper<List<NotificationModel>>> {
        return proceedFlow {
            dataSource.getNotification().data?.toNotificationList() ?: emptyList()
        }
    }


    override fun markNotification(id: Int): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.markNotification(id).success ?: false
        }
    }
}
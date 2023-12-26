package com.cious.learnhub.data.repository

import android.drm.ProcessedData
import com.cious.learnhub.data.network.api.datasource.NotificaitonDataSource
import com.cious.learnhub.data.network.api.datasource.ProfileDataSource
import com.cious.learnhub.data.network.api.datasource.ProfileDataSourceImpl
import com.cious.learnhub.data.network.api.model.notification.toNotificationList
import com.cious.learnhub.data.network.api.model.profile.toProfile
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.model.ProfileModel
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(

    ): Flow<ResultWrapper<ProfileModel>>
}

class ProfileRepositoryImpl (
    private val dataSource: ProfileDataSource
): ProfileRepository {



    override fun getProfile(): Flow<ResultWrapper<ProfileModel>> {
       return proceedFlow {
           dataSource.getProfile().profileDataResponse.toProfile()
       }
    }

}
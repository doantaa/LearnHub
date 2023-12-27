package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import com.cious.learnhub.data.network.api.service.NotificationService
import com.cious.learnhub.data.network.api.service.ProfileService

interface ProfileDataSource {
    suspend fun getProfile():  ProfileResponse
}

class ProfileDataSourceImpl(
    private val service:  ProfileService
): ProfileDataSource {
    override suspend fun getProfile(): ProfileResponse {
        return service.getDataUser()
    }


}
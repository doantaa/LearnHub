package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.profile.ChangePasswordResponse
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import com.cious.learnhub.data.network.api.model.profile.UserTransactionRespon
import com.cious.learnhub.data.network.api.service.ProfileService

interface ProfileDataSource {
    suspend fun getProfile():  ProfileResponse
    suspend fun editData(profileRequest:ProfileRequest):  ProfileResponse
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse
    suspend fun getUserTransaction(): UserTransactionRespon
}

class ProfileDataSourceImpl(
    private val service:  ProfileService
): ProfileDataSource {
    override suspend fun getProfile(): ProfileResponse {
        return service.getDataUser()
    }

    override suspend fun editData(profileRequest: ProfileRequest): ProfileResponse {
        return service.editData(profileRequest)
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse {
        return service.changePassword(changePasswordRequest)
    }

    override suspend fun getUserTransaction(): UserTransactionRespon {
        return service.getUserTransaction()
    }


}
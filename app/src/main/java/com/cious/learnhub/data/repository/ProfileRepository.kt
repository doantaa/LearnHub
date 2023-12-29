package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.ProfileDataSource
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.network.api.model.profile.toChangePasswordModel
import com.cious.learnhub.data.network.api.model.profile.toProfile
import com.cious.learnhub.data.network.api.model.profile.toUserEditModel
import com.cious.learnhub.model.ChangePasswordModel
import com.cious.learnhub.model.ProfileModel
import com.cious.learnhub.model.UserEditModel
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<ResultWrapper<ProfileModel>>

    suspend fun doEditData(profileRequest: ProfileRequest):Flow<ResultWrapper<UserEditModel>>
    suspend fun doChangePassword(changePasswordRequest: ChangePasswordRequest): Flow<ResultWrapper<ChangePasswordModel>>
}

class ProfileRepositoryImpl (
    private val dataSource: ProfileDataSource
): ProfileRepository {
    override fun getProfile(): Flow<ResultWrapper<ProfileModel>> {
        return proceedFlow {
            dataSource.getProfile().data.toProfile()
        }
    }

    override suspend fun doEditData(profileRequest: ProfileRequest): Flow<ResultWrapper<UserEditModel>> {
        return proceedFlow {
            dataSource.editData(profileRequest).toUserEditModel()
        }
    }

    override suspend fun doChangePassword(changePasswordRequest: ChangePasswordRequest): Flow<ResultWrapper<ChangePasswordModel>> {
        return  proceedFlow {
            dataSource.changePassword(changePasswordRequest).toChangePasswordModel()
        }
    }
}
package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordResponse
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import com.cious.learnhub.data.network.api.model.profile.UserTransactionResponse
import com.cious.learnhub.data.network.api.service.ProfileService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ProfileDataSourceImplTest {
    private lateinit var profileDataSource:ProfileDataSource
    @MockK
    lateinit var profileService: ProfileService
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        profileDataSource=ProfileDataSourceImpl(profileService)
    }
    @Test
    fun getProfile(){
        runTest {
            val mockkresponse= mockk<ProfileResponse>(relaxed = true)
            coEvery {
                profileService.getDataUser()
            }  returns mockkresponse
            val response=profileDataSource.getProfile()
            coVerify {
                profileService.getDataUser()
            }
            assertEquals(response,mockkresponse)
        }

    }

    @Test
    fun getUserTransaction() = runBlocking {
        val mockUserTransactionResponse = UserTransactionResponse(listOf(),"success",true)
        coEvery {
            profileService.getUserTransaction()
        }returns mockUserTransactionResponse

        val result = profileDataSource.getUserTransaction()
        assertEquals(mockUserTransactionResponse,result)
    }

    @Test
    fun changePassword() = runBlocking {
        val mockChangePasswordRequest = ChangePasswordRequest("oldPassword","newPassword","newPassword")
        val mockChangePasswordResponse = ChangePasswordResponse("success",true)

        coEvery {
            profileService.changePassword(mockChangePasswordRequest)
        }returns mockChangePasswordResponse

        val result = profileDataSource.changePassword(mockChangePasswordRequest)

        coEvery {
            profileService.changePassword(mockChangePasswordRequest)
        }

        assertEquals(mockChangePasswordResponse,result)
    }

    @Test
    fun editData() = runBlocking {
        val mockProfileRequest = ProfileRequest(
            "image",
            "maman",
            "maman@gmail.com",
            123456789,
            "indonesia",
            "bekasi"
        )
        val mockProfileResponse = mockk<ProfileResponse>(relaxed = true)

        coEvery {
            profileService.editData(mockProfileRequest)
        }returns mockProfileResponse
        val result = profileDataSource.editData(mockProfileRequest)

        coEvery {
            profileService.editData(mockProfileRequest)
        }
        assertEquals(mockProfileResponse,result)
    }
}

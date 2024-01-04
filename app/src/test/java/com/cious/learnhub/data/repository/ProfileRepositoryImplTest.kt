package com.cious.learnhub.data.repository

import app.cash.turbine.test
import com.cious.learnhub.data.network.api.datasource.ProfileDataSource
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordResponse
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import com.cious.learnhub.data.network.api.model.profile.UserTransactionItemResponse
import com.cious.learnhub.data.network.api.model.profile.UserTransactionResponse
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProfileRepositoryImplTest {

    @MockK
    private lateinit var dataSource: ProfileDataSource
    lateinit var repository: ProfileRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ProfileRepositoryImpl(dataSource)
    }

    @Test
    fun `get profile data, result loading`() = runTest{
        val mockProfile = mockk<ProfileResponse>()
        coEvery { dataSource.getProfile() } returns mockProfile

        repository.getProfile().map {
            delay(100)
            it
        }.test {
            delay(100)
            val data = expectMostRecentItem()
            Assert.assertTrue(data is ResultWrapper.Loading)
            coVerify { dataSource.getProfile() }
        }

    }


    @Test
    fun `get profile data, result success`() = runTest{
        val mockProfile = mockk<ProfileResponse>(relaxed = true)
        coEvery { dataSource.getProfile() } returns mockProfile

        repository.getProfile().map {
            delay(100)
            it
        }.test {
            delay(201)
            val data = expectMostRecentItem()
            Assert.assertTrue(data is ResultWrapper.Success)
            coVerify { dataSource.getProfile() }
        }

    }


    @Test
    fun `do edit data, result loading`() = runTest{
        val mockRequest = mockk<ProfileRequest>()
        val mockResponse = mockk<ProfileResponse>(relaxed = true)
        coEvery { dataSource.editData(any()) } returns mockResponse

        repository.doEditData(mockRequest).map {
            delay(100)
            it
        }.test {
            delay(101)
            val data = expectMostRecentItem()
            Assert.assertTrue(data is ResultWrapper.Loading)
            coVerify { dataSource.editData(any()) }
        }

    }


    @Test
    fun `do change password, result success`() = runTest{
        val mockRequest = mockk<ChangePasswordRequest>()
        val mockResponse = mockk<ChangePasswordResponse>(relaxed = true)
        coEvery { dataSource.changePassword(any()) } returns mockResponse

        repository.doChangePassword(mockRequest).map {
            delay(100)
            it
        }.test {
            delay(201)
            val data = expectMostRecentItem()
            Assert.assertTrue(data is ResultWrapper.Success)
            coVerify { dataSource.changePassword(any()) }
        }

    }



    @Test
    fun `get user transaction, result loading`() = runTest {
        val mockResponse = mockk<UserTransactionResponse>(relaxed = true)
        coEvery { dataSource.getUserTransaction() } returns mockResponse

        repository.getUserTransaction().map {
            delay(100)
            it
        }.test {
            delay(101)
            val data = expectMostRecentItem()
            println(data)
            Assert.assertTrue(data is ResultWrapper.Loading)
            coVerify { dataSource.getUserTransaction() }
        }
    }
        @Test
        fun `get user transaction, result success`() = runTest{
            val mockDetailResponse = mockk<UserTransactionItemResponse>(relaxed = true)
            val mockResponse = UserTransactionResponse(data = listOf(mockDetailResponse), "message", true)
            coEvery { dataSource.getUserTransaction() } returns mockResponse

            repository.getUserTransaction().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                Assert.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getUserTransaction() }
            }

    }



}

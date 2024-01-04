package com.cious.learnhub.ui.profile.editprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.ProfileModel
import com.cious.learnhub.model.UserEditModel
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.tools.getOrAwaitValue
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class EditProfileViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: ProfileRepository
    lateinit var viewModel: EditProfileViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val mockUserEditResponse = mockk<UserEditModel>()
        coEvery { repository.doEditData(any()) } returns flow {
            emit(
              ResultWrapper.Success(
                  mockUserEditResponse
              )
            )
        }

        val mockUserProfile = mockk<ProfileModel>()
        coEvery { repository.getProfile() } returns flow {
            emit(
                ResultWrapper.Success(
                    mockUserProfile
                )
            )
        }

        viewModel = spyk(
            EditProfileViewModel(repository)
        )
    }

    @Test
    fun `edit data, get live data result`(){
        val mockRequest = mockk<ProfileRequest>()
        viewModel.doEditData(mockRequest)
        val result = viewModel.profilefRequestResult.getOrAwaitValue()
        coVerify { repository.doEditData(any()) }
        Assert.assertTrue(result.payload is UserEditModel)
    }

    @Test
    fun `get user live data`(){
        val result = viewModel.profile.getOrAwaitValue()
        coVerify { repository.getProfile() }
        Assert.assertTrue(result.payload is ProfileModel)
    }
}
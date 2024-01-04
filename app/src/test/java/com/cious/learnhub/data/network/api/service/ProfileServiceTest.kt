package com.cious.learnhub.data.network.api.service

import com.cious.learnhub.data.network.api.model.profile.ProfileDataResponse
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

val dummyProfileData = ProfileDataResponse(
    city = "jakarta",
    country = "indonesia",
    email = "maman@gmail.com",
    id = 123,
    membership = "Gold",
    name = "John Doe",
    phoneNumber = "1234567890",
    profileUrl = "maman.jpg",
    role = "User"
)

val dummyProfileResponse = ProfileResponse(
    data = dummyProfileData,
    message = "Data profile ditemukan",
    success = true
)
class ProfileServiceTest {


    @Mock
    private lateinit var mockProfileService: ProfileService

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getDataUser() = runBlocking {
        `when`(mockProfileService.getDataUser()).thenReturn(dummyProfileResponse)

        val actualResponse = mockProfileService.getDataUser()

        assertEquals(dummyProfileData,actualResponse.data)
        assertEquals("Data profile ditemukan",actualResponse.message)
        assertEquals(true,actualResponse.success)
    }

}
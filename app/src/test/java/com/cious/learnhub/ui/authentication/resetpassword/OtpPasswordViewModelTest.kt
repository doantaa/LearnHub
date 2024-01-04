package com.cious.learnhub.ui.authentication.resetpassword

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.model.UserResetData
import com.cious.learnhub.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class OtpPasswordViewModelTest {

    @MockK
    private lateinit var extras: Bundle

    private val mockBundle = mockk<Bundle>(relaxed = true)

    @get: Rule
    val testRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule: TestRule = MainCoroutineRule()

    private lateinit var viewModel: OtpPasswordViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(OtpPasswordViewModel(mockk()))
    }

    @Test
    fun `get dataParcel`() {
        val userResetData = mockk<UserResetData>(relaxed = true)
        every {  }
        val result = viewModel.dataParcel
        assertEquals(result, 0)
    }
}
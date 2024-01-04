package com.cious.learnhub.ui.authentication.resetpassword

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.model.LoginData
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

    @get: Rule
    val testRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule: TestRule = MainCoroutineRule()

    private lateinit var viewModel: OtpPasswordViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        extras = mockk(relaxed = true)

        val responseData = mockk<UserResetData>(relaxed = true)
        coEvery { extras.getParcelable<UserResetData>("USER_RESET_DATA") } returns responseData

        viewModel = spyk(OtpPasswordViewModel(extras))
    }

    @Test
    fun `get dataParcel`() {
        val result = viewModel.dataParcel
        assertTrue(result is UserResetData)
    }
}
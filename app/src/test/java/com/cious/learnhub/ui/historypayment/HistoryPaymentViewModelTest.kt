package com.cious.learnhub.ui.historypayment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HistoryPaymentViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: ProfileRepository

    lateinit var viewModel: HistoryPaymentViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = spyk(
            HistoryPaymentViewModel(repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `get transaction live data`(){

        coEvery { repository.getUserTransaction() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        viewModel.getTransaction()

    }
}
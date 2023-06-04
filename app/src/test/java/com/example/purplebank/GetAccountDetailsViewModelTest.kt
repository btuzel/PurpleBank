package com.example.purplebank

import app.cash.turbine.test
import com.example.purplebank.data.user.User
import com.example.purplebank.data.user.UserResult
import com.example.purplebank.presentation.getaccountdetails.GetAccountDetailsUseCase
import com.example.purplebank.presentation.getaccountdetails.GetAccountDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAccountDetailsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val user = mockk<User>(relaxed = true)
    private val getAccountDetailsUseCase = mockk<GetAccountDetailsUseCase> {
        coEvery {
            this@mockk()
        } returns UserResult.Success(user)
    }

    private lateinit var testSubject: GetAccountDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testSubject = GetAccountDetailsViewModel(getAccountDetailsUseCase)
    }

    @Test
    fun `return success state`() =
        runTest {
            testSubject.accountUiState.test {
                assertEquals(GetAccountDetailsViewModel.AccountViewState.Loading, awaitItem())
                assertEquals(
                    GetAccountDetailsViewModel.AccountViewState.Success(user),
                    awaitItem()
                )
            }
        }

    @Test
    fun `return error state`() =
        runTest {
            val errorMessage = "ERROR"
            val getAccountDetailsUseCaseFailure = mockk<GetAccountDetailsUseCase> {
                coEvery {
                    this@mockk()
                } returns UserResult.Failure(errorMessage)
            }
            testSubject = GetAccountDetailsViewModel(getAccountDetailsUseCaseFailure)
            testSubject.accountUiState.test {
                assertEquals(GetAccountDetailsViewModel.AccountViewState.Loading, awaitItem())
                assertEquals(
                    GetAccountDetailsViewModel.AccountViewState.Error(errorMessage),
                    awaitItem()
                )
            }
        }
}
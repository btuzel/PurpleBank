package com.example.purplebank

import app.cash.turbine.test
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.transaction.transactionresponse.NewBalance
import com.example.purplebank.data.transaction.transactionresponse.SendMoneyResponse
import com.example.purplebank.data.user.MyBalance
import com.example.purplebank.data.user.MyMostRecentTransaction
import com.example.purplebank.data.user.User
import com.example.purplebank.data.user.UserResult
import com.example.purplebank.presentation.getaccountdetails.GetAccountDetailsUseCase
import com.example.purplebank.presentation.transaction.SendMoneyUseCase
import com.example.purplebank.presentation.transaction.SendMoneyViewModel
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
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class SendMoneyViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val user = User(
        id = "0",
        myBalance = MyBalance(Amount(100, 100), "GBP"),
        myMostRecentTransactions = listOf(
            MyMostRecentTransaction(
                TransactionAmount(Amount(100, 100), "GBP"), "",
                ",",
                ",",
                "",
            )
        ),
        name = "Jane"
    )
    private val getAccountDetailsUseCase = mockk<GetAccountDetailsUseCase> {
        coEvery {
            this@mockk()
        } returns UserResult.Success(
            user
        )
    }

    private val successResult = "ok"
    private val successNewBalance = NewBalance("GBP", Amount(100, 100))
    private val sendMoneyUseCase = mockk<SendMoneyUseCase> {
        coEvery {
            this@mockk(any(), any())
        } returns SendMoneyResponse(successResult, successNewBalance, "")
    }

    private val failResult = "failed"
    private val failReason = "failblabla"
    private val sendMoneyUseCaseFail = mockk<SendMoneyUseCase> {
        coEvery {
            this@mockk(any(), any())
        } returns SendMoneyResponse(failResult, null, failReason)
    }

    private lateinit var testSubject: SendMoneyViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testSubject = SendMoneyViewModel(sendMoneyUseCase, getAccountDetailsUseCase)
    }

    @Test
    fun `get success state after initiating send money`() = runTest {
        testSubject.uiState.test {
            assertEquals(SendMoneyViewModel.UiState.Loading, awaitItem())
            testSubject.sendMoney(BigDecimal(100.100), "Ross")
            skipItems(1)
            assertEquals(
                SendMoneyViewModel.UiState.AccountState(
                    user.myBalance.amount,
                    "",
                    "Your new balance is 100 pounds and 100 pence."
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `get fail state after initiating send money`() = runTest {
        testSubject = SendMoneyViewModel(sendMoneyUseCaseFail, getAccountDetailsUseCase)
        testSubject.uiState.test {
            assertEquals(SendMoneyViewModel.UiState.Loading, awaitItem())
            testSubject.sendMoney(BigDecimal(100.100), "Ross")
            skipItems(1)
            assertEquals(
                SendMoneyViewModel.UiState.AccountState(user.myBalance.amount, "", failReason),
                awaitItem()
            )
        }
    }
}
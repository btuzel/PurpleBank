package com.example.purplebank

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.presentation.transaction.SendMoneyScreen
import com.example.purplebank.presentation.transaction.SendMoneyViewModel
import com.example.purplebank.ui.theme.PurpleBankTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SendMoneyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val robot = SendMoneyScreenTestRobot(composeTestRule)

    private val currentBalanceAmount = Amount(100, 100)

    private val uiStateFlow = MutableStateFlow<SendMoneyViewModel.UiState>(
        SendMoneyViewModel.UiState.AccountState(
            amountToSend = "0",
            currentBalance = currentBalanceAmount,
            returnMessage = ""
        )
    )

    private val viewModel = mockk<SendMoneyViewModel>(relaxUnitFun = true) {
        every { uiState } returns uiStateFlow
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            PurpleBankTheme {
                SendMoneyScreen(
                    sendMoneyViewModel = viewModel
                )
            }
        }
    }

    @Test
    fun sendMoneyTitleTextPresent() = robot.execute {
        sendMoneyTitleTextIsPresent()
    }

    @Test
    fun balanceTextIsPresent() = robot.execute {
        balanceTextIsPresent(currentBalanceAmount)
    }

    @Test
    fun otfExists() = robot.execute {
        outlinedTextFieldIsPresent()
    }

    @Test
    fun whenPutBalanceGreaterThanExistingBalanceButtonDisabled() = robot.execute {
        put100BalanceMoreThanCurrentInTextField(currentBalanceAmount)
        assertSendButtonIsDisabled()
    }

    @Test
    fun whenPutBalanceLessThanExistingBalanceButtonEnabled() = robot.execute {
        put10BalanceLessThanCurrentInTextField(currentBalanceAmount)
        assertSendButtonIsEnabled()
    }
}
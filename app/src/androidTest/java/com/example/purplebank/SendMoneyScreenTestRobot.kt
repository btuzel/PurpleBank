package com.example.purplebank

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.presentation.transaction.SEND_MONEY_TEXT_FIELD

class SendMoneyScreenTestRobot(composeTestRule: ComposeTestRule) : BaseRobot(composeTestRule) {


    fun sendMoneyTitleTextIsPresent() {
        composeTestRule.onNodeWithText("Put the amount of money you would like to send:")
            .assertIsDisplayed()
    }

    fun outlinedTextFieldIsPresent() {
        composeTestRule.onNodeWithTag(SEND_MONEY_TEXT_FIELD).assertIsDisplayed()
    }

    fun balanceTextIsPresent(currentBalanceAmount: Amount) {
        composeTestRule.onNodeWithText(currentBalanceAmount.units.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(currentBalanceAmount.subUnits.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText("GBP").assertIsDisplayed()
    }

    fun assertSendButtonIsDisabled() {
        composeTestRule.onNodeWithText("Send").assertIsNotEnabled()
    }

     fun assertSendButtonIsEnabled() {
        composeTestRule.onNodeWithText("Send").assertIsEnabled()
    }

    fun put100BalanceMoreThanCurrentInTextField(currentBalanceAmount: Amount) {
        composeTestRule.onNodeWithTag(SEND_MONEY_TEXT_FIELD)
            .performTextReplacement((currentBalanceAmount.units + 100).toString())
    }

    fun put10BalanceLessThanCurrentInTextField(currentBalanceAmount: Amount) {
        composeTestRule.onNodeWithTag(SEND_MONEY_TEXT_FIELD).performClick()
        composeTestRule.onNodeWithTag(SEND_MONEY_TEXT_FIELD)
            .performTextReplacement((currentBalanceAmount.units - 20).toString())
    }
}

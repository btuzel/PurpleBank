package com.example.purplebank.network.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.purplebank.R
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.ui.PurpleBankButton
import java.math.BigDecimal

@Composable
fun SendMoneyScreen(
    sendMoneyViewModel: SendMoneyViewModel = hiltViewModel(),
) {
    val moneyState by sendMoneyViewModel.uiState.collectAsState()
    SendMoneyScreenInternal(
        moneyState,
        sendMoneyViewModel::valueChanged,
        sendMoneyViewModel::sendMoney
    )
}

@Composable
fun SendMoneyScreenInternal(
    moneyState: SendMoneyViewModel.UiState,
    valueChanged: (String) -> Unit,
    sendMoney: (BigDecimal, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        when (moneyState) {
            is SendMoneyViewModel.UiState.AccountState -> {
                Row {
                    Text(text = moneyState.currentBalance.units.toString() + ",")
                    Text(text = moneyState.currentBalance.subUnits.toString())
                    Text(text = "GBP")
                }
                Text(text = "Put the amount of money you would like to send:")
                OutlinedTextField(
                    value = moneyState.amountToSend,
                    onValueChange = {
                        if (it.matches(Regex("\\d*\\.?\\d{0,2}")) && !it.contains(",")) {
                            valueChanged(it)
                        }
                    },
                    label = { Text("Enter value") },
                    placeholder = { Text("0") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                PurpleBankButton(
                    text = R.string.money_send,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = moneyState.amountToSend.isNotEmpty() && checkShit(
                        moneyState.currentBalance,
                        moneyState.amountToSend
                    )
                ) {
                    sendMoney(moneyState.amountToSend.toBigDecimal(), "000")
                }
            }

            SendMoneyViewModel.UiState.Loading -> {}
            is SendMoneyViewModel.UiState.Transferred -> Text(text = moneyState.returnMessage)
        }
    }
}

private fun checkShit(amount: Amount, amountToSend: String): Boolean {
    val newAmountToSend = amountToSend.replace(",", ".")
    val decimalAmountToSend = try {
        BigDecimal(newAmountToSend)
    } catch (e: NumberFormatException) {
        return false
    }

    return if (amount.subUnits > 0) {
        val newAmount = BigDecimal.valueOf(amount.units.toLong())
            .multiply(BigDecimal.valueOf(100))
            .add(BigDecimal.valueOf(amount.subUnits.toLong()))
        newAmount >= decimalAmountToSend.multiply(BigDecimal.valueOf(100))
    } else {
        BigDecimal.valueOf(amount.units.toLong()) >= decimalAmountToSend
    }
}
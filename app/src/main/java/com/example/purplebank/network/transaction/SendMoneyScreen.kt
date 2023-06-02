package com.example.purplebank.network.transaction

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.purplebank.R
import com.example.purplebank.helper.HelperFunctions
import com.example.purplebank.helper.checkEntry
import com.example.purplebank.ui.LoadingState
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SendMoneyScreenInternal(
    moneyState: SendMoneyViewModel.UiState,
    valueChanged: (String) -> Unit,
    sendMoney: (BigDecimal, String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (moneyState) {
            is SendMoneyViewModel.UiState.AccountState -> {
                if (moneyState.returnMessage.isNotEmpty()) {
                    Toast.makeText(
                        LocalContext.current,
                        moneyState.returnMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Row {
                    Text(text = moneyState.currentBalance.units.toString() + ",")
                    Text(text = moneyState.currentBalance.subUnits.toString())
                    Text(text = "GBP")
                }
                Text(text = "Put the amount of money you would like to send:")
                OutlinedTextField(
                    value = moneyState.amountToSend,
                    onValueChange = {
                        if (it.checkEntry() || it.isEmpty()) {
                            valueChanged(it)
                        }
                    },
                    label = { Text("Enter transfer amount") },
                    placeholder = { Text("0") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged { state ->
                            if (state.isFocused) {
                                keyboardController?.show()
                            } else {
                                keyboardController?.hide()
                            }
                        }
                )
                PurpleBankButton(
                    text = R.string.money_send,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = moneyState.amountToSend.isNotEmpty() && HelperFunctions().evaluateBalance(
                        moneyState.currentBalance,
                        moneyState.amountToSend
                    ) && moneyState.amountToSend > 0.toString()
                ) {
                    sendMoney(moneyState.amountToSend.toBigDecimal(), "000")
                    keyboardController?.hide()
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }

            SendMoneyViewModel.UiState.Loading -> LoadingState()
        }
    }
}
package com.example.purplebank.presentation.transaction

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.purplebank.R
import com.example.purplebank.helper.checkEntry
import com.example.purplebank.helper.evaluateBalance
import com.example.purplebank.ui.LoadingState
import com.example.purplebank.ui.PurpleBankButton
import java.math.BigDecimal

const val SEND_MONEY_TEXT_FIELD = "sendMoneyTextField"
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
                Text(
                    text = "Put the amount of money you would like to send:",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "${moneyState.currentBalance.units}",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = ".${moneyState.currentBalance.subUnits}",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "GBP",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                OutlinedTextField(
                    value = moneyState.amountToSend,
                    onValueChange = { newValue ->
                        if (newValue.checkEntry() || newValue.isEmpty()) {
                            valueChanged(newValue)
                        }
                    },
                    label = { Text("Enter transfer amount") },
                    placeholder = { Text("0") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(SEND_MONEY_TEXT_FIELD)
                        .padding(horizontal = 32.dp)
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
                    text = stringResource(R.string.money_send),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    enabled = moneyState.amountToSend.isNotEmpty() && evaluateBalance(
                        moneyState.currentBalance,
                        moneyState.amountToSend
                    ) && moneyState.amountToSend > "0"
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
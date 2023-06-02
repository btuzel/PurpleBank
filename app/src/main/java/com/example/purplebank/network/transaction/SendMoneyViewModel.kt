package com.example.purplebank.network.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.transaction.transactionresponse.SendMoneyResult
import com.example.purplebank.network.getaccountdetails.GetAccountDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val sendMoneyUseCase: SendMoneyUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase
) :
    ViewModel() {

    private val initialUiState = UiState.Loading
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        initialUiState
    )
    val uiState: StateFlow<UiState> = _uiState

    private var currentBalance = Amount(0, 0)

    init {
        viewModelScope.launch {
            getBalance()
        }
    }

    private suspend fun getBalance() {
        val account = getAccountDetailsUseCase()
        currentBalance = account.myBalance.amount
        _uiState.value = UiState.AccountState(
            currentBalance = currentBalance,
            amountToSend = "",
            returnMessage = ""
        )
    }

    fun sendMoney(
        amount: BigDecimal,
        targetUser: String
    ) {
        val intValue = amount.toInt()
        val remainder = amount.remainder(BigDecimal.ONE).toInt()
        val transactionAmount = TransactionAmount(
            amount = Amount(subUnits = intValue, units = remainder),
            currency = "GBP"
        )
        viewModelScope.launch {
            when (val result = sendMoneyUseCase(transactionAmount, targetUser)) {
                is SendMoneyResult.Failure -> _uiState.value =
                    UiState.AccountState(
                        currentBalance = currentBalance,
                        amountToSend = "",
                        returnMessage = result.failureReason
                    )

                is SendMoneyResult.Success -> _uiState.value =
                    UiState.AccountState(
                        currentBalance = currentBalance,
                        amountToSend = "",
                        returnMessage = "Your transaction was successful! Your new balance is ${result.newBalance.amount.units} pound and ${result.newBalance.amount.subUnits} pennies."
                    )
            }
        }
    }

    fun valueChanged(value: String) {
        _uiState.value = UiState.AccountState(
            currentBalance = currentBalance,
            amountToSend = value,
            returnMessage = ""
        )
    }


    sealed class UiState {
        object Loading : UiState()
        data class AccountState(
            val currentBalance: Amount,
            val amountToSend: String,
            val returnMessage: String
        ) : UiState()
    }
}
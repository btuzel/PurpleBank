package com.example.purplebank.network.getaccountdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.user.MyBalance
import com.example.purplebank.data.user.MyMostRecentTransaction
import com.example.purplebank.data.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//sending money -> Request(50, hardcoded maybe the account that will receive the money)
//receiving money -> get account details(refresh)

@HiltViewModel
class GetAccountDetailsViewModel @Inject constructor(private val getAccountDetailsUseCase: GetAccountDetailsUseCase) :
    ViewModel() {

    private val initialUiState = AccountState(
        User(
            id = "000",
            myBalance = MyBalance(Amount(0, 0), "GBP"),
            myMostRecentTransactions = listOf(
                MyMostRecentTransaction(
                    TransactionAmount(Amount(0, 0), "GBP"),
                    "9",
                    "2",
                    "2",
                    "2"
                )
            ),
            name = "Jane"
        )
    )

    private val _accountUiState: MutableStateFlow<AccountState> = MutableStateFlow(initialUiState)
    val accountUiState: StateFlow<AccountState> = _accountUiState

    init {
        viewModelScope.launch {
            _accountUiState.value = AccountState(getAccountDetailsUseCase())
        }
    }

    data class AccountState(
        val userAccount: User
    )
}
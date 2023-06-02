package com.example.purplebank.network.getaccountdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purplebank.data.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//sending money -> Request(50, hardcoded maybe the account that will receive the money)
//receiving money -> get account details(refresh)

@HiltViewModel
class GetAccountDetailsViewModel @Inject constructor(private val getAccountDetailsUseCase: GetAccountDetailsUseCase) :
    ViewModel() {

    private val _accountUiState: MutableStateFlow<AccountViewState> =
        MutableStateFlow(AccountViewState.Loading)
    val accountUiState: StateFlow<AccountViewState> = _accountUiState

    init {
        viewModelScope.launch {
            try {
                val account = getAccountDetailsUseCase()
                _accountUiState.value = AccountViewState.Success(account)
            } catch (e: Exception) {
                _accountUiState.value =
                    AccountViewState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class AccountViewState {
        object Loading : AccountViewState()
        data class Success(val userAccount: User) : AccountViewState()
        data class Error(val errorMessage: String) : AccountViewState()
    }
}
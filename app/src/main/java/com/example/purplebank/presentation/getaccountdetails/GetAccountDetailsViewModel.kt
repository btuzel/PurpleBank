package com.example.purplebank.presentation.getaccountdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purplebank.data.user.User
import com.example.purplebank.data.user.UserResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAccountDetailsViewModel @Inject constructor(private val getAccountDetailsUseCase: GetAccountDetailsUseCase) :
    ViewModel() {

    private val _accountUiState: MutableStateFlow<AccountViewState> =
        MutableStateFlow(AccountViewState.Loading)
    val accountUiState: StateFlow<AccountViewState> = _accountUiState

    init {
        viewModelScope.launch {
            getAccount()
        }
    }

     fun getAccount() {
        viewModelScope.launch {
            when (val account = getAccountDetailsUseCase()) {
                is UserResult.Success ->
                    _accountUiState.value = AccountViewState.Success(
                        userAccount = account.user
                    )

                is UserResult.Failure ->
                    _accountUiState.value = AccountViewState.Error(account.failureReason)
            }
        }
    }

    sealed class AccountViewState {
        object Loading : AccountViewState()
        data class Success(val userAccount: User) : AccountViewState()
        data class Error(val errorMessage: String) : AccountViewState()
    }
}
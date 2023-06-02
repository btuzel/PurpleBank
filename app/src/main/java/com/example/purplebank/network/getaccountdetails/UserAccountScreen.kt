package com.example.purplebank.network.getaccountdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.purplebank.ui.PurpleBankButton

@Composable
fun UserAccountScreen(
    getAccountDetailsViewModel: GetAccountDetailsViewModel = hiltViewModel(),
    goToSendMoneyScreen: () -> Unit
) {
    val account by getAccountDetailsViewModel.accountUiState.collectAsState()
    UserAccountInternal(account = account, goToSendMoneyScreen = goToSendMoneyScreen)
}

@Composable
fun UserAccountInternal(
    account: GetAccountDetailsViewModel.AccountState,
    goToSendMoneyScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        PurpleBankButton(text = "Send Money") {
            goToSendMoneyScreen()
        }
        Text(text = account.userAccount.id)
        Text(text = "Account Name: ${account.userAccount.name}")
        Text(text = "Currency Type: ${account.userAccount.myBalance.currency}")
        Row {
            Text(text = account.userAccount.myBalance.amount.subUnits.toString() + ",")
            Text(text = account.userAccount.myBalance.amount.units.toString())
        }
        Text(text = "Recent Transactions")
        val transactions = account.userAccount.myMostRecentTransactions
        LazyColumn {
            items(transactions) { transaction ->
                Text(text = transaction.date)
                Text(text = transaction.reference)
                Text(text = transaction.amount.currency)
                Row {
                    Text(text = transaction.amount.amount.subUnits.toString() + ",")
                    Text(text = transaction.amount.amount.units.toString())
                }
                Text(text = transaction.sender)
                Text(text = transaction.direction)
            }
        }
    }
}

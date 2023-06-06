package com.example.purplebank.presentation.getaccountdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.purplebank.R
import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.user.MyMostRecentTransaction
import com.example.purplebank.data.user.User
import com.example.purplebank.helper.fixDate
import com.example.purplebank.ui.ErrorState
import com.example.purplebank.ui.LoadingState
import com.example.purplebank.ui.PurpleBankButton

@Composable
fun GetAccountDetailsScreen(
    getAccountDetailsViewModel: GetAccountDetailsViewModel = hiltViewModel(),
    goToSendMoneyScreen: () -> Unit
) {
    val accountViewState by getAccountDetailsViewModel.accountUiState.collectAsState()
    when (val account = accountViewState) {
        is GetAccountDetailsViewModel.AccountViewState.Error -> ErrorState(getAccountDetailsViewModel::getAccount)
        GetAccountDetailsViewModel.AccountViewState.Loading -> LoadingState()
        is GetAccountDetailsViewModel.AccountViewState.Success -> {
            GetAccountDetailsScreenInternal(account.userAccount, goToSendMoneyScreen)
        }
    }
}

@Composable
fun GetAccountDetailsScreenInternal(
    account: User,
    goToSendMoneyScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.account_name, account.name),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.currency_type, account.myBalance.currency),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                AmountRow(account.myBalance.amount)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.recent_transactions),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(account.myMostRecentTransactions) { transaction ->
                    TransactionItem(transaction)
                    if (transaction != account.myMostRecentTransactions.last()) {
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
        PurpleBankButton(text = stringResource(R.string.send_money)) {
            goToSendMoneyScreen()
        }
    }
}

@Composable
fun TransactionItem(transaction: MyMostRecentTransaction) {
    val bgColor =
        if (transaction.direction == Direction.OUTGOING.stringValue) Color.Green else Color.Red
    Column(modifier = Modifier
        .background(bgColor)
        .fillMaxWidth()) {
        Text(text = stringResource(id = R.string.date, fixDate(transaction.date)))
        Text(text = stringResource(id = R.string.reference, transaction.reference))
        Text(text = stringResource(id = R.string.currency_type, transaction.amount.currency))
        AmountRow(transaction.amount.amount)
        Text(text = transaction.sender ?: "No sender")
        Text(text = transaction.direction)
    }
}

@Composable
fun AmountRow(amount: Amount) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = amount.units.toString(), style = MaterialTheme.typography.h4)
        Text(text = ",", style = MaterialTheme.typography.h4)
        Text(
            text = amount.subUnits.toString(),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 6.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "GBP",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

enum class Direction(val stringValue: String) {
    OUTGOING("outgoing"),
    INCOMING("incoming")
}
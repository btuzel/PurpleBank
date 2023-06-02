package com.example.purplebank.network.getaccountdetails

import com.example.purplebank.data.transaction.Amount
import com.example.purplebank.data.transaction.TransactionAmount
import com.example.purplebank.data.user.MyBalance
import com.example.purplebank.data.user.MyMostRecentTransaction
import com.example.purplebank.data.user.User
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(private val getAccountDetailsService: GetAccountDetailsService) {

    suspend operator fun invoke(): User {
         return getAccountDetailsService.getUserAccount(API_TOKEN)
//        return User(
//            id = "123",
//            myBalance = MyBalance(Amount(13, 4), "GBP"),
//            myMostRecentTransactions = listOf(
//                MyMostRecentTransaction(
//                    TransactionAmount(Amount(13, 123), "GBP"),
//                    "20230602T21:45:00",
//                    "outgoing",
//                    "abuz",
//                    "alivelideli"
//                ),
//                MyMostRecentTransaction(
//                    TransactionAmount(Amount(14, 1234), "TL"),
//                    "20230602T21:45:00",
//                    "asd",
//                    "qwe",
//                    "ggggg"
//                ),
//                MyMostRecentTransaction(
//                    TransactionAmount(Amount(1, 123), "GBP"),
//                    "20230602T21:45:00",
//                    "outgoing",
//                    "abuz",
//                    "alivelideli"
//                )
//            ),
//            name = "Jane"
//        )
    }

}

const val API_TOKEN = "1231231232"
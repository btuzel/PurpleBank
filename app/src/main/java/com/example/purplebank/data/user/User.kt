package com.example.purplebank.data.user

data class User(
    val id: String,
    val myBalance: MyBalance,
    val myMostRecentTransactions: List<MyMostRecentTransaction>,
    val name: String
)
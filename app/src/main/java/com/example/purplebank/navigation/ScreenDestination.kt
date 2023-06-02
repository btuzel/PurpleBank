package com.example.purplebank.navigation

sealed class ScreenDestination(val route: String) {

    object SendMoneyScreen : ScreenDestination(route = "sendmoney")

    object UserAccountScreen :
        ScreenDestination(route = "useraccount")
}
package com.example.purplebank.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.purplebank.presentation.getaccountdetails.UserAccountScreen
import com.example.purplebank.presentation.transaction.SendMoneyScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenDestination.UserAccountScreen.route
    ) {
        composable(route = ScreenDestination.SendMoneyScreen.route) {
            SendMoneyScreen()
        }
        composable(route = ScreenDestination.UserAccountScreen.route) {
            UserAccountScreen(goToSendMoneyScreen = { navHostController.navigate(ScreenDestination.SendMoneyScreen.route) })
        }
    }
}

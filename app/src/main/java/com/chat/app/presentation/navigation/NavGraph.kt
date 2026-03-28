package com.chat.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chat.app.presentation.chat.ChatScreen
import com.chat.app.presentation.login.LoginScreen

private const val ROUTE_LOGIN = "login"
private const val ROUTE_CHAT  = "chat"

@Composable
fun ChatNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_LOGIN) {
        composable(ROUTE_LOGIN) {
            LoginScreen(
                onLoggedIn = {
                    navController.navigate(ROUTE_CHAT) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(ROUTE_CHAT) {
            ChatScreen(
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_CHAT) { inclusive = true }
                    }
                }
            )
        }
    }
}

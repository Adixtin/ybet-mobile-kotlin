package com.chat.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chat.app.presentation.chat.ChatScreen
import com.chat.app.presentation.login.LoginScreen
import com.chat.app.presentation.serverconfig.ServerConfigScreen
import com.chat.app.presentation.settings.SettingsScreen

private const val ROUTE_SERVER_CONFIG = "server_config"
private const val ROUTE_LOGIN         = "login"
private const val ROUTE_CHAT          = "chat"
private const val ROUTE_SETTINGS      = "settings"

@Composable
fun ChatNavGraph(
    isDarkTheme: Boolean = true,
    onToggleTheme: () -> Unit = {},
    fontSizeMultiplier: Float = 1.0f,
    onFontSizeChange: (Float) -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_SERVER_CONFIG) {

        composable(ROUTE_SERVER_CONFIG) {
            ServerConfigScreen(
                onConfirmed = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_SERVER_CONFIG) { inclusive = true }
                    }
                }
            )
        }

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
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                onNavigateToSettings = {
                    navController.navigate(ROUTE_SETTINGS)
                },
                onLogout = {
                    navController.navigate(ROUTE_SERVER_CONFIG) {
                        popUpTo(ROUTE_CHAT) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTE_SETTINGS) {
            SettingsScreen(
                fontSizeMultiplier = fontSizeMultiplier,
                onFontSizeChange = onFontSizeChange,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

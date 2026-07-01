package com.benimhesabim.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.benimhesabim.app.feature.auth.LoginScreen
import com.benimhesabim.app.feature.auth.RegisterScreen
import com.benimhesabim.app.feature.auth.SplashScreen
import com.benimhesabim.app.feature.home.HomeScreen
import com.benimhesabim.app.feature.settings.SettingsScreen
import com.benimhesabim.app.feature.transaction.AddTransactionScreen
import com.benimhesabim.app.feature.transaction.TransactionListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash
    ) {
        composable(AppDestination.Splash) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(AppDestination.Login) {
                        popUpTo(AppDestination.Splash) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(AppDestination.Home) {
                        popUpTo(AppDestination.Splash) { inclusive = true }
                    }
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppDestination.Login) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(AppDestination.Register) },
                onLoginSuccess = {
                    navController.navigate(AppDestination.Home) {
                        popUpTo(AppDestination.Login) { inclusive = true }
                    }
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppDestination.Register) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(AppDestination.Home) {
                        popUpTo(AppDestination.Register) { inclusive = true }
                    }
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppDestination.Home) {
            HomeScreen(
                onAddTransaction = { navController.navigate(AppDestination.AddTransaction) },
                onOpenTransactions = { navController.navigate(AppDestination.TransactionList) },
                onOpenSettings = { navController.navigate(AppDestination.Settings) },
                onLogout = {
                    navController.navigate(AppDestination.Login) {
                        popUpTo(AppDestination.Home) { inclusive = true }
                    }
                },
                viewModel = hiltViewModel()
            )
        }
        composable(AppDestination.AddTransaction) {
            AddTransactionScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
        composable(AppDestination.TransactionList) {
            TransactionListScreen(
                onBack = { navController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
        composable(AppDestination.Settings) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(AppDestination.Login) {
                        popUpTo(AppDestination.Home) { inclusive = true }
                    }
                },
                viewModel = hiltViewModel()
            )
        }
    }
}

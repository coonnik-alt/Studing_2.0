package com.example.test_application.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test_application.components.BottomBar
import com.example.test_application.screens.AccountScreen
import com.example.test_application.screens.FavoritesScreen
import com.example.test_application.screens.LogIn
import com.example.test_application.screens.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute != Destination.LOGIN.route

    Scaffold(
        containerColor = Color(0xFF151515),
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.LOGIN.route,
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF151515))
        ) {
            composable(Destination.LOGIN.route) {
                LogIn(
                    onLoginClick = {
                        navController.navigate(Destination.MAIN.route) {
                            popUpTo(Destination.LOGIN.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Destination.MAIN.route) {
                MainScreen()
            }

            composable(Destination.FAVORITES.route) {
                FavoritesScreen()
            }

            composable(Destination.ACCOUNT.route) {
                AccountScreen()
            }
        }
    }
}
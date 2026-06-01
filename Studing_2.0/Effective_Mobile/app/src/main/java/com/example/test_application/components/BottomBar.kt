package com.example.test_application.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test_application.Navigation.Destination
import com.example.test_application.R

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar(
        modifier = Modifier.height(72.dp),
        containerColor = Color(0xFF24252B)
    ) {
        NavigationBarItem(
            selected = currentRoute == Destination.MAIN.route,
            onClick = {
                navController.navigate(Destination.MAIN.route) {
                    popUpTo(Destination.MAIN.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = "Главная"
                )
            },
            label = {
                Text(text = "Главная")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF12B956),
                selectedTextColor = Color(0xFF12B956),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == Destination.FAVORITES.route,
            onClick = {
                navController.navigate(Destination.FAVORITES.route) {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = "Избранное"
                )
            },
            label = {
                Text(text = "Избранное")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF12B956),
                selectedTextColor = Color(0xFF12B956),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == Destination.ACCOUNT.route,
            onClick = {
                navController.navigate(Destination.ACCOUNT.route) {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_account),
                    contentDescription = "Аккаунт"
                )
            },
            label = {
                Text(text = "Аккаунт")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF12B956),
                selectedTextColor = Color(0xFF12B956),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}
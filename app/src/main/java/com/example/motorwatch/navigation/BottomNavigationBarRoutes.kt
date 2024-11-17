package com.example.motorwatch.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Security
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationBarRoutes(
    val route: String,
    val contentDescription: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home: BottomNavigationBarRoutes("Home", "Home icon for home bottom navigation item", Icons.Filled.Home, Icons.Outlined.Home)
    object Policies: BottomNavigationBarRoutes("Policies", "Policies icon for Policies bottom navigation item", Icons.Filled.Security, Icons.Outlined.Security)
    object Claims: BottomNavigationBarRoutes("Claims", "Claims icon for Claims bottom navigation item", Icons.Filled.Assignment, Icons.Outlined.Assignment)
    object Profile: BottomNavigationBarRoutes("Profile", "Profile icon for Profile bottom navigation item", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
}
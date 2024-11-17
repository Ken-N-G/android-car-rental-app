package com.example.motorwatch.ui.widget

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.motorwatch.navigation.BottomNavigationBarRoutes
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
   navController: NavController
) {
    val screens = listOf(
        BottomNavigationBarRoutes.Home,
        BottomNavigationBarRoutes.Policies,
        BottomNavigationBarRoutes.Claims,
        BottomNavigationBarRoutes.Profile
    )
    NavigationBar(
        modifier = modifier,
        containerColor = WhiteBackground
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = backStackEntry?.destination?.route
        repeat(screens.size) { page ->
            NavigationBarItem(
                selected = screens[page].route == currentScreen,
                onClick = {
                   navController.navigate(screens[page].route)
                },
                icon = {
                    when(screens[page].route == currentScreen) {
                        true -> Icon(
                            imageVector = screens[page].selectedIcon,
                            contentDescription = screens[page].contentDescription,
                            tint = YellowSecondary
                        )
                        false -> Icon(
                            imageVector = screens[page].unselectedIcon,
                            contentDescription = screens[page].contentDescription,
                            tint = ReducedLightGrey
                        )
                    }
                },
                label = {
                    when(screens[page].route == currentScreen) {
                        true -> Text(
                            screens[page].route,
                            style = MaterialTheme.typography.bodySmall,
                            color = YellowSecondary
                        )
                        false -> Text(
                            screens[page].route,
                            style = MaterialTheme.typography.bodySmall,
                            color = ReducedLightGrey
                        )
                    }
                }
            )
        }
    }
}
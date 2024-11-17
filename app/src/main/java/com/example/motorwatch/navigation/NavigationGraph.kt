package com.example.motorwatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.motorwatch.ui.screen.ApplyPolicyPage
import com.example.motorwatch.ui.screen.ClaimDetailsPage
import com.example.motorwatch.ui.screen.ClaimsPage
import com.example.motorwatch.ui.screen.FillClaimPage
import com.example.motorwatch.ui.screen.HomePage
import com.example.motorwatch.ui.screen.LoginPage
import com.example.motorwatch.ui.screen.MakeClaimPage
import com.example.motorwatch.ui.screen.PoliciesPage
import com.example.motorwatch.ui.screen.PolicyDetailsPage
import com.example.motorwatch.ui.screen.PolicyListPage
import com.example.motorwatch.ui.screen.ProfilePage
import com.example.motorwatch.ui.screen.RegisterPage

@Composable
fun NavigationGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.Login.route
    ) {
        composable(route = ScreenRoutes.Home.route) {
           HomePage(navController = navController)
        }
        composable(route = ScreenRoutes.Policies.route) {
            PoliciesPage(navController = navController)
        }
        composable(route = ScreenRoutes.Claims.route) {
            ClaimsPage(navController = navController)
        }
        composable(route = ScreenRoutes.Profile.route) {
            ProfilePage(navController = navController)
        }
        composable(
            route = ScreenRoutes.PolicyDetail.route,
            arguments = listOf(navArgument("policyholderId") {
                type = NavType.StringType
            })
        ) {
            PolicyDetailsPage(
                policyId = it.arguments?.getString("policyholderId") ?: "0",
                navController = navController
            )
        }
        composable(
            route = ScreenRoutes.ClaimDetail.route,
            arguments = listOf(navArgument("claimId") {
                type = NavType.StringType
            })
        ) {
            ClaimDetailsPage(
                claimId = it.arguments?.getString("claimId") ?: "0",
                navController = navController
            )
        }
        composable(route = ScreenRoutes.PolicyList.route) {
            PolicyListPage(navController = navController)
        }
        composable(
            route = ScreenRoutes.ApplyPolicy.route,
            arguments = listOf(navArgument("policyId") {
                type = NavType.StringType
            })
        ) {
            ApplyPolicyPage(
                policyId = it.arguments?.getString("policyId") ?: "0",
                navController = navController
            )
        }
        composable(route = ScreenRoutes.MakeClaim.route) {
            MakeClaimPage(navController = navController)
        }
        composable(
            route = ScreenRoutes.FillClaim.route,
            arguments = listOf(navArgument("policyholderId") {
                type = NavType.StringType
            })
        ) {
            FillClaimPage(
                policyholderId = it.arguments?.getString("policyholderId") ?: "0",
                navController = navController
            )
        }
        composable(route = ScreenRoutes.Login.route) {
            LoginPage(navController = navController)
        }
        composable(route = ScreenRoutes.Register.route) {
            RegisterPage(navController = navController)
        }

    }
}
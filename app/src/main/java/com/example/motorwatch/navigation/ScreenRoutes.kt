package com.example.motorwatch.navigation

sealed class ScreenRoutes(val route: String) {
    object Login: ScreenRoutes("Login")
    object Register: ScreenRoutes("Register")
    object Home: ScreenRoutes("Home")
    object Policies: ScreenRoutes("Policies")
    object Claims: ScreenRoutes("Claims")
    object Profile: ScreenRoutes("Profile")
    object PolicyList: ScreenRoutes("PolicyList")
    object ApplyPolicy: ScreenRoutes("ApplyPolicy/{policyId}")
    object MakeClaim: ScreenRoutes("MakeClaim")
    object FillClaim: ScreenRoutes("FillClaim/{policyholderId}")
    object PolicyDetail: ScreenRoutes("PolicyDetail/{policyholderId}")
    object ClaimDetail: ScreenRoutes("ClaimDetail/{claimId}")
}
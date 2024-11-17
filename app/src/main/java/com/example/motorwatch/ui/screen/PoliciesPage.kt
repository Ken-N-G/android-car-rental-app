package com.example.motorwatch.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.theme.MotorWatchTheme
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary
import com.example.motorwatch.ui.viewmodel.HomeViewModel
import com.example.motorwatch.ui.viewmodel.PoliciesViewModel
import com.example.motorwatch.ui.widget.BottomNavigationBar
import com.example.motorwatch.ui.widget.CarouselIndicator
import com.example.motorwatch.ui.widget.CustomRefreshButton
import com.example.motorwatch.ui.widget.HomePageActionButton
import com.example.motorwatch.ui.widget.MainScreenHeader
import com.example.motorwatch.ui.widget.VehiclePolicyCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PoliciesPage(
    modifier: Modifier = Modifier,
    viewModel: PoliciesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = Unit) {
        viewModel.GetData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WhiteBackground,
                    titleContentColor = ReducedBlack,
                ),
                title = {
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(ScreenRoutes.Profile.route)
                        }
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "dew",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            )
        },
        bottomBar = {
            Column {
                Divider(color = ReducedLightGrey, thickness = 1.dp)
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
            innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(WhiteBackground)
                .fillMaxWidth()
                .fillMaxHeight()
                .absolutePadding(
                    top = innerPadding.calculateTopPadding() + 20.dp,
                    left = 20.dp,
                    right = 20.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                )
        ) {
            item {
                Column {
                    MainScreenHeader(
                        title = "Your Policies",
                        subTitle = "Manage your policies",
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
            items(
                viewModel.policiesState.value.policyholders.size
            ) { index ->
                Button(
                    onClick = {
                        navController.navigate("PolicyDetail/" + viewModel.policiesState.value.policyholders[index].policyholderId.toString())
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    VehiclePolicyCard(
                        width = screenWidth - 40.dp,
                        vehicleIdentificationNumber = viewModel.policiesState.value.policyholders[index].licensePlate,
                        policyName = "Motor Guard Plus",
                        carModel = viewModel.policiesState.value.policyholders[index].carModel,
                        policyId = viewModel.policiesState.value.policyholders[index].policyholderId.toString(),
                        policyExpirationDate = viewModel.policiesState.value.policyholders[index].expirationDate.date.toString()
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PolicyPagePreview() {
    MotorWatchTheme {
        PoliciesPage()
    }
}*/

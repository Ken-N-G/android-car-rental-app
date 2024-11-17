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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.ShoppingCart
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary
import com.example.motorwatch.ui.viewmodel.HomeViewModel
import com.example.motorwatch.ui.viewmodel.RegisterViewModel
import com.example.motorwatch.ui.widget.BottomNavigationBar
import com.example.motorwatch.ui.widget.CarouselIndicator
import com.example.motorwatch.ui.widget.HomePageActionButton
import com.example.motorwatch.ui.widget.MainScreenHeader
import com.example.motorwatch.ui.widget.VehiclePolicyCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {


    val coroutineScope = rememberCoroutineScope()

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
                            contentDescription = "",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
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
        Column(
            modifier = Modifier
                .background(WhiteBackground)
                .fillMaxHeight()
                .fillMaxHeight()
                .absolutePadding(
                    top = innerPadding.calculateTopPadding() + 20.dp,
                    left = 0.dp,
                    right = 0.dp,
                    bottom = 0.dp,
                )
        ) {
            MainScreenHeader(
                title = "Welcome,",
                subTitle = viewModel.homeState.value.fullname,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)) {
                Text(
                    text = "Our Services",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HomePageActionButton(
                        imageVector = Icons.Outlined.ShoppingCart,
                        text = "Buy\nPolicies",
                        onClick = {
                            navController.navigate(ScreenRoutes.PolicyList.route)
                        }
                    )
                    HomePageActionButton(
                        imageVector = Icons.Outlined.Assignment,
                        text = "Make\nClaim",
                        onClick = {
                            navController.navigate(ScreenRoutes.MakeClaim.route)
                        }
                    )
                }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    MotorWatchTheme {
        HomePage()
    }
}*/

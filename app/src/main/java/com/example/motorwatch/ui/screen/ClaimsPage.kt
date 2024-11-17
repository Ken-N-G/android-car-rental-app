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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.data.model.ClaimStatus
import com.example.motorwatch.navigation.ScreenRoutes
import com.example.motorwatch.ui.theme.MotorWatchTheme
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.ReducedGrey
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary
import com.example.motorwatch.ui.viewmodel.ClaimViewModel
import com.example.motorwatch.ui.viewmodel.PoliciesViewModel
import com.example.motorwatch.ui.widget.BottomNavigationBar
import com.example.motorwatch.ui.widget.CarouselIndicator
import com.example.motorwatch.ui.widget.ClaimCard
import com.example.motorwatch.ui.widget.CustomSearchField
import com.example.motorwatch.ui.widget.HomePageActionButton
import com.example.motorwatch.ui.widget.MainScreenHeader
import com.example.motorwatch.ui.widget.VehiclePolicyCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ClaimsPage(
    modifier: Modifier = Modifier,
    viewModel: ClaimViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {
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
                title = "Claims",
                subTitle = "Review your filed claims",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(modifier = Modifier.absolutePadding(left = 20.dp, right = 20.dp, bottom = innerPadding.calculateBottomPadding())) {
                items(
                    viewModel.claimSate.value.claims.size,
                ) { index ->
                    val status = evaluateStatus(viewModel.claimSate.value.claims[index].status)
                    Spacer(modifier = Modifier.height(10.dp))
                    ClaimCard(
                        onClick = {
                              navController.navigate("ClaimDetail/" + viewModel.claimSate.value.claims[index].claimId.toString())
                        },
                        claimId = viewModel.claimSate.value.claims[index].claimId.toString(),
                        claimDate = viewModel.claimSate.value.claims[index].dateMade.date.toString(),
                        claimLocation = viewModel.claimSate.value.claims[index].serviceShop,
                        claimStatus = status
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

fun evaluateStatus(status: String): ClaimStatus {
    when(status) {
        "submitted" -> return ClaimStatus.Submitted
        "review in-progress" -> return ClaimStatus.Review
        "rejected" -> return ClaimStatus.Rejected
        "approved" -> return ClaimStatus.Approved
    }
    return ClaimStatus.Review
}

/*
@Preview(showBackground = true)
@Composable
fun ClaimsPagePreview() {
    MotorWatchTheme {
        ClaimsPage()
    }
}*/

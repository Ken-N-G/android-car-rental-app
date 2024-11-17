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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.motorwatch.ui.theme.ReducedGrey
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.theme.YellowSecondary
import com.example.motorwatch.ui.viewmodel.HomeViewModel
import com.example.motorwatch.ui.viewmodel.PolicyListViewModel
import com.example.motorwatch.ui.widget.BottomNavigationBar
import com.example.motorwatch.ui.widget.CarouselIndicator
import com.example.motorwatch.ui.widget.CustomBottomButton
import com.example.motorwatch.ui.widget.HomePageActionButton
import com.example.motorwatch.ui.widget.MainScreenHeader
import com.example.motorwatch.ui.widget.VehiclePolicyCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PolicyListPage(
    modifier: Modifier = Modifier,
    viewModel: PolicyListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.GetData()
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                          navController.popBackStack()
                    },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(
                    "Coverage Policies",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        },
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
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column {
                    Text(
                        "How can we protect you?",
                        color = ReducedGrey,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            items(
                viewModel.policyListState.value.policies.size,
            ) { index ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = WhiteBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            viewModel.policyListState.value.policies[index].name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            viewModel.policyListState.value.policies[index].description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Column {
                            for (s in viewModel.SplitBenefitString(viewModel.policyListState.value.policies[index].benefits)) {
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    s,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Maximum Annual Coverage",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                viewModel.policyListState.value.policies[index].annualCoverage.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Deductible per Visit",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                viewModel.policyListState.value.policies[index].deductible.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Yearly Premium",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                viewModel.policyListState.value.policies[index].yearlyPremium.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        CustomBottomButton(text = "Apply Now") {
                            navController.navigate("ApplyPolicy/" + viewModel.policyListState.value.policies[index].policyId.toString())
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PolicyListPagePreview() {
    MotorWatchTheme {
        PolicyListPage()
    }
}*/

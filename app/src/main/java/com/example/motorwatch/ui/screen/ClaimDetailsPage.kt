package com.example.motorwatch.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motorwatch.data.model.ClaimStatus
import com.example.motorwatch.ui.theme.MotorWatchTheme
import com.example.motorwatch.ui.theme.ReducedBlack
import com.example.motorwatch.ui.theme.ReducedLightGrey
import com.example.motorwatch.ui.theme.WhiteBackground
import com.example.motorwatch.ui.viewmodel.ClaimDetailViewModel
import com.example.motorwatch.ui.viewmodel.ClaimViewModel
import com.example.motorwatch.ui.widget.BottomNavigationBar
import com.example.motorwatch.ui.widget.CardField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ClaimDetailsPage(
    claimId: String,
    viewModel: ClaimDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.GetData(claimId.toInt())
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
                     "Claim - ${viewModel.claimDetailState.value.claimId}",
                     style = MaterialTheme.typography.titleLarge,
                 )
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
                    left = 30.dp,
                    right = 30.dp,
                    bottom = 0.dp,
                )
        ) {
            Spacer(modifier = Modifier.height(30.dp))
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
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "Claim Details",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    CardField(
                        fieldName = "Claim Reason",
                        fieldValue = viewModel.claimDetailState.value.claimReason
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CardField(
                        fieldName = "Service Shop",
                        fieldValue = viewModel.claimDetailState.value.serviceShop
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CardField(
                        fieldName = "Date of Claim",
                        fieldValue = viewModel.claimDetailState.value.dateMade?.date.toString() ?: "0000-00-00"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CardField(
                        fieldName = "Claim Status",
                        fieldValue = viewModel.claimDetailState.value.status
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
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
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "Vehicle Details",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    CardField(
                        fieldName = "License Plate",
                        fieldValue = viewModel.claimDetailState.value.licensePlate
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CardField(
                        fieldName = "Car Model",
                        fieldValue = viewModel.claimDetailState.value.carModel
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CardField(
                        fieldName = "Registered Postcode",
                        fieldValue = viewModel.claimDetailState.value.postcode.toString()
                    )
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ClaimDetailsPreview() {
    MotorWatchTheme {
        ClaimDetailsPage()
    }
}*/
